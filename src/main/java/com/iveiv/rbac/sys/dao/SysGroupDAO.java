package com.iveiv.rbac.sys.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iveiv.rbac.domain.po.SysGroupPO;

/**
 * sys账户分组
 * @author irays
 *
 */
public interface SysGroupDAO extends JpaRepository<SysGroupPO, Long> {

}
