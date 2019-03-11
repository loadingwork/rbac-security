package com.iveiv.rbac.sys.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.iveiv.rbac.domain.po.SysErrorLogPO;


/**
 * 系统日记持久化
 * @author irays
 *
 */
public interface SysErrorLogDAO extends JpaRepository<SysErrorLogPO, Long>, JpaSpecificationExecutor<SysErrorLogPO> {

}
