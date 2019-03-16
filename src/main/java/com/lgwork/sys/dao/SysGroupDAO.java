package com.lgwork.sys.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lgwork.domain.po.SysGroupPO;

/**
 * sys账户分组
 * @author irays
 *
 */
public interface SysGroupDAO extends JpaRepository<SysGroupPO, Long> {

}
