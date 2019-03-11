package com.iveiv.rbac.sys.service;

import org.springframework.data.domain.Page;

import com.iveiv.rbac.domain.po.SysErrorLogPO;

/**
 * 系统错误日记服务接口
 * @author irays
 *
 */
public interface SysErrorLogService {

	
	/**
	 * 搜索数据展示
	 * @param pageNum
	 * @param pageSize
	 * @param fgetSysErrorLogPO
	 * @return
	 */
	Page<SysErrorLogPO> pageSearchByCondition(Integer pageNum, Integer pageSize, SysErrorLogPO fgetSysErrorLogPO);

}
