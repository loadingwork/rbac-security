package com.iveiv.rbac.sys.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iveiv.rbac.api.exceptions.BizException;
import com.iveiv.rbac.domain.po.SysRandomStrPO;
import com.iveiv.rbac.enums.SysRandomStrTypeEnum;
import com.iveiv.rbac.sys.dao.SysRandomStrDAO;
import com.iveiv.rbac.sys.service.SysRandomStrService;
import com.iveiv.rbac.util.RandomStrUtils;

import lombok.extern.slf4j.Slf4j;


/**
 * 随机字符串服务实现
 * @author irays
 *
 */
@Slf4j
@Service
public class SysRandomStrServiceImpl  implements SysRandomStrService {
	
	
	/**
	 * 随机数持久化接口
	 */
	@Autowired
	private SysRandomStrDAO sysRandomStrDAO;

	// 声明全局锁
	private Lock lock = new ReentrantLock();
	
	
	@Transactional(rollbackFor=Exception.class)
	@Override
	public  SysRandomStrPO  getOneSysRandomStr(SysRandomStrTypeEnum type) throws InterruptedException {
		
		if (type == null) {
			throw new IllegalArgumentException("参数异常");
		}
		
		// --- 常量
		final int length = type.value();
		final String desc = type.desc();
		
		// 固定次数
		final int fixedTimes = 5;
		// 固定时间
		final int fixedTime = 5;
		
		// 加锁
		if (lock.tryLock(fixedTime, TimeUnit.SECONDS)) { 
			// 获取到锁
			try {
				
				for(int currentTimes = 0; currentTimes < fixedTimes; currentTimes++ ) {
					try {
						
						// 获取随机数
						String random = "";
						
						switch (type) {
						case NUM:
							random = RandomStrUtils.genIntStr(length);
							break;
						default:
							random = RandomStrUtils.genLowerStr(length);
							break;
						}
						
						// 判断数据是否存在
						boolean existsByContentAndType = sysRandomStrDAO.existsByContentAndType(random, desc);
						
						if (existsByContentAndType) {
							// 已经存在
							log.debug("随机数: {}, 类型: {} 已经存在", random, length);
							continue;
						}
						
						// 构建并保存随机数
						SysRandomStrPO sysRandomStrPO = new SysRandomStrPO();
						// 初始化
//						BasePO.initData(sysRandomStrPO);
						
						sysRandomStrPO.setContent(random);
						sysRandomStrPO.setLength(length);
						sysRandomStrPO.setType(desc);
						DateTime dateTime = new DateTime();
						// 增加一个小时
						DateTime plusOneHours = dateTime.plusHours(1);
						sysRandomStrPO.setExpiresAt(plusOneHours.toDate());
						sysRandomStrPO.setUsed(false);
						
						sysRandomStrPO =sysRandomStrDAO.save(sysRandomStrPO);
						
						// 终止for循环
						return sysRandomStrPO;
					} catch (Exception e) {
						log.debug(e.getMessage(), e);
					}
				}
				
				
				
			} catch (Exception e) {
				log.debug(e.getMessage(), e);
			} finally {
				// 释放锁
				lock.unlock();
			}
			
			// 尝试次数过多
			throw BizException.TOO_MANY_ATTEMPTS;
        } else {
        	// 枷锁超时
        	throw BizException.TIMEOUT_ATTEMPT;
        }
		
		
		
	}



	@Transactional(rollbackFor=Exception.class)
	@Override
	public List<SysRandomStrPO> listSysRandomStr(SysRandomStrTypeEnum type, int needNum)
			throws InterruptedException {
		
		if (type == null ||  needNum <=0) {
			throw new IllegalArgumentException("参数异常");
		}
		
		// --- 常量区
		final int length = type.getValue();
		final String desc = type.getDesc();
		
		// 固定时间
		final int fixedTime = 5;
		
		// 加锁
		if (lock.tryLock(fixedTime, TimeUnit.SECONDS)) { 
			// 获取到锁
			try {
				
				// 从数据库中获取所有类型数据
				List<String> listSysRandomStr = sysRandomStrDAO.findByType(desc).stream().map(item -> {
					return item.getContent();
				}).collect(Collectors.toList());
						
				
				// 固定次数
				final int fixedTimes = 5;
				
				List<SysRandomStrPO> needSysRandomStrPO = new ArrayList<SysRandomStrPO>(needNum);
				
				// 构建数据
				for(int itemNeedNum = 0; itemNeedNum < needNum; itemNeedNum++) {
					
					// 获取一个随机数
					for(int currentTimes = 0; currentTimes < fixedTimes; currentTimes++ ) {
						// 获取随机数
						String random = "";
						switch (type) {
						case NUM:
							random = RandomStrUtils.genIntStr(length);
							break;
						default:
							random = RandomStrUtils.genLowerStr(length);
							break;
						}
						
						if (listSysRandomStr.contains(random)) {
							continue;
						}
						// 添加到集合中
						listSysRandomStr.add(random);
						
						// 构建并保存随机数
						SysRandomStrPO sysRandomStrPO = new SysRandomStrPO();
						// 初始化
//						BasePO.initData(sysRandomStrPO);
						sysRandomStrPO.setContent(random);
						sysRandomStrPO.setLength(length);
						sysRandomStrPO.setType(desc);
						DateTime dateTime = new DateTime();
						// 增加一个小时
						DateTime plusOneHours = dateTime.plusHours(1);
						sysRandomStrPO.setExpiresAt(plusOneHours.toDate());
						sysRandomStrPO.setUsed(false);
						
						// 添加到结果中
						needSysRandomStrPO.add(sysRandomStrPO);
						// 跳出最近一层循环
						break;
					}
					
				}
				
				// 批量保存
				sysRandomStrDAO.saveAll(needSysRandomStrPO);
				
				return needSysRandomStrPO;
			} catch (Exception e) {
				log.debug(e.getMessage(), e);
			} finally {
				// 释放锁
				lock.unlock();
			}
			
			// 尝试次数过多
			throw BizException.TOO_MANY_ATTEMPTS;
        } else {
        	// 枷锁超时
        	throw BizException.TIMEOUT_ATTEMPT;
        }
	}
	
	
	
	
	
	

}
