package com.iveiv.rbac.sys.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iveiv.rbac.domain.po.SysMenuPermissionPO;

/**
 * 菜单与权限关联
 * @author irays
 *
 */
public interface SysMenuPermissionDAO extends JpaRepository<SysMenuPermissionPO, Long> {

}
