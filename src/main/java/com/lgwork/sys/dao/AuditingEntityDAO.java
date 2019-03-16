package com.lgwork.sys.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lgwork.domain.po.AuditingEntityPO;

public interface AuditingEntityDAO extends JpaRepository<AuditingEntityPO, Long> {

}
