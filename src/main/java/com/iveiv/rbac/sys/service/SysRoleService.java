package com.iveiv.rbac.sys.service;

import org.springframework.data.domain.Page;

import com.iveiv.rbac.base.result.BaseResult;
import com.iveiv.rbac.domain.po.SysRolePO;


/**
 * 角色管理服务接口
 * @author irays
 *
 */
public interface SysRoleService {

	
	/**
	 * 
	 * 分页搜索角色列表
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @param fgetSysRolePO
	 * @return
	 */
	Page<SysRolePO> pageSearchByCondition(Integer pageNum, Integer pageSize, final SysRolePO fgetSysRolePO);

	
	/**
	 * 创建角色
	 * @param fgetSysRolePO
	 * @return
	 */
	BaseResult<String> saveSysRole(SysRolePO fgetSysRolePO);
	
	/**
	 * 根据id获取信息
	 * @param id
	 * @return
	 */
	SysRolePO getById(Long id);

	
	/**
	 * 根据id修改
	 * @param id
	 * @param fgetSysRolePO
	 * @return
	 */
	BaseResult<String> updateSysRolePO(Long id, SysRolePO fgetSysRolePO);


	/**
	 * 根据id删除
	 * @param id
	 * @return
	 */
	BaseResult<String> deleteSysRoleById(Long id);
	

}
