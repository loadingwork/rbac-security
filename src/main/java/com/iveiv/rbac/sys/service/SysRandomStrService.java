package com.iveiv.rbac.sys.service;

import java.util.List;

import com.iveiv.rbac.domain.po.SysRandomStrPO;
import com.iveiv.rbac.enums.SysRandomStrTypeEnum;

/**
 * 随机字符串服务接口
 * @author irays
 *
 */
public interface SysRandomStrService {
	
	
	
	/**
	 *  获取一个随机数
	 * @param type  数据类型
	 * @param length  长度
	 * @return
	 */
	SysRandomStrPO getOneSysRandomStr(SysRandomStrTypeEnum type) throws InterruptedException;
	
	
	/**
	 * 获取多个
	 * @param type
	 * @param length
	 * @param needNum
	 * @return
	 * @throws InterruptedException
	 */
	@Deprecated
	List<SysRandomStrPO> listSysRandomStr(SysRandomStrTypeEnum type, int needNum) throws InterruptedException;
	
	

}
