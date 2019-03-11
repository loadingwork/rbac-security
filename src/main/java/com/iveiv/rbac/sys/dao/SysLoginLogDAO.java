package com.iveiv.rbac.sys.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.iveiv.rbac.domain.po.SysLoginLogPO;


/**
 * 系统用户登录日记
 * @author irays
 *
 */
public interface SysLoginLogDAO  extends JpaRepository<SysLoginLogPO, Long>, JpaSpecificationExecutor<SysLoginLogPO> {

}
