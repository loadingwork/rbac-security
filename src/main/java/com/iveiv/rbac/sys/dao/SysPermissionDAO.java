package com.iveiv.rbac.sys.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.iveiv.rbac.domain.po.SysPermissionPO;

/**
 * 权限表
 * @author irays
 *
 */
public interface SysPermissionDAO extends JpaRepository<SysPermissionPO, Long>, JpaSpecificationExecutor<SysPermissionPO> {

}
