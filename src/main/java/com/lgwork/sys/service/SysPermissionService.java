package com.lgwork.sys.service;

import org.springframework.data.domain.Page;

import com.lgwork.base.result.BaseResult;
import com.lgwork.domain.po.SysPermissionPO;

/**
 * 权限服务接口
 * @author irays
 *
 */
public interface SysPermissionService {

	
	/**
	 * 根据id删除数据
	 * @param id
	 * @return
	 */
	BaseResult<String> deleteById(Long id);

	
	/**
	 * 获取搜索结果
	 * @param pageNum
	 * @param pageSize
	 * @param fgetSysPermissionPO
	 * @return
	 */
	Page<SysPermissionPO> pageSearchByCondition(Integer pageNum, Integer pageSize, SysPermissionPO fgetSysPermissionPO);

}
