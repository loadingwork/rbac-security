package com.iveiv.rbac.sys.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iveiv.rbac.domain.po.SysGroupRolePO;

/**
 * sys账户与用户组关联持久化
 * @author irays
 *
 */
public interface SysGroupRoleDAO extends JpaRepository<SysGroupRolePO, Long> {

}
