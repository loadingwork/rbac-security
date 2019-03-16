package com.lgwork.sys.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lgwork.domain.po.SysOperationPermissionPO;

/**
 * 操作权限关联表
 * @author irays
 *
 */
public interface SysOperationPermissionDAO  extends JpaRepository<SysOperationPermissionPO, Long> {

}
