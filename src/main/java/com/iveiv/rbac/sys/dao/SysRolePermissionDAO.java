package com.iveiv.rbac.sys.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iveiv.rbac.domain.po.SysRolePermissionPO;

/**
 * 角色与权限关联持久化接口
 * @author irays
 *
 */
public interface SysRolePermissionDAO extends JpaRepository<SysRolePermissionPO, Long> {

}
