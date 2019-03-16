package com.lgwork.sys.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.lgwork.domain.po.SysLoginLogPO;


/**
 * 系统用户登录日记
 * @author irays
 *
 */
public interface SysLoginLogDAO  extends JpaRepository<SysLoginLogPO, Long>, JpaSpecificationExecutor<SysLoginLogPO> {

}
