package com.iveiv.rbac.sys.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iveiv.rbac.domain.po.AuditingEntityPO;

public interface AuditingEntityDAO extends JpaRepository<AuditingEntityPO, Long> {

}
