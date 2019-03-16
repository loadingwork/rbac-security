package com.lgwork.sys.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lgwork.domain.po.SysUserRolePO;

/**
 * 系统用户与角色关系持久化接口
 * @author irays
 *
 */
public interface SysUserRoleDAO  extends JpaRepository<SysUserRolePO, Long> {

}
