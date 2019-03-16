package com.lgwork.sys.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.lgwork.domain.po.SysRolePO;


/**
 * 角色持久化接口
 * @author irays
 *
 */
public interface SysRoleDAO  extends JpaRepository<SysRolePO, Long>, JpaSpecificationExecutor<SysRolePO> {
	
	
	/**
	 * 根据roleCode编码获取信息
	 * @param roleCode
	 * @return
	 */
	SysRolePO findByRoleCode(String roleCode);
	

}
