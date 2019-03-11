package com.iveiv.rbac.sys.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.iveiv.rbac.domain.po.FileStoragePO;


/**
 * 
 * 文件储存
 * 
 * @author irays
 *
 */
public interface FileStorageDAO extends JpaRepository<FileStoragePO, Long>, JpaSpecificationExecutor<FileStoragePO> {
	
	
	/**
	 * 根据code获取文件
	 * @param fileCode
	 * @return
	 */
	FileStoragePO findByFileCode(String fileCode);
	

}
