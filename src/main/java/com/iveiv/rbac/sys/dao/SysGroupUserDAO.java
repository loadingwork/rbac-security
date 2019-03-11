package com.iveiv.rbac.sys.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iveiv.rbac.domain.po.SysGroupUserPO;

/**
 * 系统组持久化
 * @author irays
 *
 */
public interface SysGroupUserDAO  extends JpaRepository<SysGroupUserPO, Long> {

}
