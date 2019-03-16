package com.lgwork.sys.service;

import org.springframework.web.multipart.MultipartFile;

import com.lgwork.domain.dto.FileUploadRespDTO;

/**
 * 文件持久化到本地
 * @author irays
 *
 */
public interface FilePersistLocalService {
	
	
	/**
	  * 单个文件上传额
	  * @param file  文件
	  */
	FileUploadRespDTO handleSaveSingleFile(MultipartFile file, String type) throws Exception;
	

}
