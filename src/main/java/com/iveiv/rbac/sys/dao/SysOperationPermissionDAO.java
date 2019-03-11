package com.iveiv.rbac.sys.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iveiv.rbac.domain.po.SysOperationPermissionPO;

/**
 * 操作权限关联表
 * @author irays
 *
 */
public interface SysOperationPermissionDAO  extends JpaRepository<SysOperationPermissionPO, Long> {

}
