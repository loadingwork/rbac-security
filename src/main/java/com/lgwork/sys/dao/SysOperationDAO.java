package com.lgwork.sys.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.lgwork.domain.po.SysOperationPO;

/**
 * 操作权限表
 * @author irays
 *
 */
public interface SysOperationDAO  extends JpaRepository<SysOperationPO, Long>, JpaSpecificationExecutor<SysOperationPO> {
	
	/**
	 * 根据编码获取
	 * @param operationCode
	 * @return
	 */
	Optional<SysOperationPO>  findByOperationCode(String operationCode);

}
