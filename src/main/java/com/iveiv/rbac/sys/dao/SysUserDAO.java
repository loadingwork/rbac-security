package com.iveiv.rbac.sys.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iveiv.rbac.domain.po.SysUserPO;

/**
 * 
 * 系统用户账号持久化接口
 * 
 * @author irays
 *
 */
public interface SysUserDAO extends JpaRepository<SysUserPO, Long> {
	
	
	/**
	 * 根据编码查询
	 * @param ucode
	 * @return
	 */
	SysUserPO findByUcode(String ucode);
	
	
	
	
	

}
