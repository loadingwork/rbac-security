package com.iveiv.rbac.sys.service.impl;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iveiv.rbac.base.constant.RedisConst;
import com.iveiv.rbac.domain.po.QrcodeTokenLoginPO;
import com.iveiv.rbac.enums.QrcodeTokenStatusEnum;
import com.iveiv.rbac.sys.dao.QrcodeTokenLoginDAO;
import com.iveiv.rbac.sys.service.LoginService;
import com.iveiv.rbac.util.UuidUtil;

import lombok.extern.slf4j.Slf4j;


/**
 * 
 * 登录服务
 * 
 * @author irays
 *
 */
@Slf4j
@Service
public class LoginServiceImpl implements LoginService {
	
	/**
	 * redis操作
	 */
	@Autowired
	private RedisTemplate<String, String>  redisTemplate;
	
	/**
	 * token持久化
	 */
	@Autowired
	private QrcodeTokenLoginDAO qrcodeTokenLoginDAO;
	

	@Override
	public String createQrcodeToken() {
		
		// 创建qrToken
		
		String qrToken = UuidUtil.uuid32();
		
		String key = RedisConst.QRCODE_LOGIN_PROFIX + qrToken;
		
		// 操作string
		ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
		
		// 保存5分钟
		opsForValue.set(key, QrcodeTokenStatusEnum.START.value(), 300, TimeUnit.SECONDS);
		
		return qrToken;
	}

	
	
	@Override
	public String getQrcodeTokenFromRedis(String qrcodeToken) {
		
		if(StringUtils.isEmpty(qrcodeToken)) {
			return null;
		}
		
		String key = RedisConst.QRCODE_LOGIN_PROFIX + qrcodeToken;
		
		// 操作string
		ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
		
		// 获取
		return opsForValue.get(key);
	}



	
	@Transactional(rollbackFor=Exception.class)
	@Override
	public void updateQrcodeTokenToRedis(String qrcodeToken, String status) {
		if(StringUtils.isEmpty(qrcodeToken)) {
			throw new RuntimeException("参数异常");
		}
		
		String key = RedisConst.QRCODE_LOGIN_PROFIX + qrcodeToken;
		
		// 操作string
		ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
		
		// 修改
		opsForValue.set(key, status);
	}



	@Transactional(rollbackFor=Exception.class)
	@Override
	public String extractUsernameByQrcodeToken(String qrcodeToken, String clientId, String clientToken) {
		
		// 根据qrcodeToken获取数据
		QrcodeTokenLoginPO qrcodeTokenLoginPO = qrcodeTokenLoginDAO.findByQrcodeToken(qrcodeToken);
		
		if(qrcodeTokenLoginPO == null) {
			log.debug("qrcodeToken: {} 不存在", qrcodeToken);
			return null;
		}
		
		// 判断
		if(StringUtils.isAnyEmpty(qrcodeTokenLoginPO.getClientId(), qrcodeTokenLoginPO.getClientToken())) {
			return null;
		}
		
		if(qrcodeTokenLoginPO.getClientId().equals(clientId)
				&& qrcodeTokenLoginPO.getClientToken().equals(clientToken)) {
			
			// 修改状态
			qrcodeTokenLoginPO.setLoginTime(new Date());
			qrcodeTokenLoginPO.setUsed(true);
			
			qrcodeTokenLoginDAO.save(qrcodeTokenLoginPO);
			
			return qrcodeTokenLoginPO.getUsername();
		}
		
		
		return null;
	}
	
	

}
