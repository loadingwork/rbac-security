package com.iveiv.rbac.sys.service;

import org.springframework.data.domain.Page;

import com.iveiv.rbac.domain.po.SysLoginLogPO;

/**
 * 系统登录log服务接口
 * @author irays
 *
 */
public interface SysLoginLogService {

	
	/**
	 * 搜索列表展示
	 * @param pageNum
	 * @param pageSize
	 * @param fgetSysLoginLogPO
	 * @return
	 */
	Page<SysLoginLogPO> pageSearchByCondition(Integer pageNum, Integer pageSize, SysLoginLogPO fgetSysLoginLogPO);
	
	

}
