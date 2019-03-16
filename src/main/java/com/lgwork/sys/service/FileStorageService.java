package com.lgwork.sys.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.lgwork.base.result.BaseResult;
import com.lgwork.domain.dto.FileUploadRespDTO;
import com.lgwork.domain.po.FileStoragePO;

/**
 * 
 * 文件储存服务接口
 * 
 * @author irays
 *
 */
public interface FileStorageService {
	
	 
	 /**
	  * 单个文件上传额
	  * @param file  文件
	  * @param code  业务代码
	  * @return
	  */
	 BaseResult<FileUploadRespDTO> uploadSingleFile(MultipartFile file, String type) throws Exception;

	 /**
	  * 多个文件上传
	  * @param fileMap
	  * @param code
	  * @return
	  */
	 BaseResult<List<FileUploadRespDTO>> uploadMultipleFile(List<MultipartFile> files, String type) throws Exception;
	
	/**
	 * 根据code获取文件
	 * @param code
	 * @return
	 */
	 FileStoragePO  getByFileCode(String code);

	 
	 /**
	  *  分页获取参数
	  * @param pageNum
	  * @param pageSize
	  * @param fgetFileStoragePO
	  * @return
	  */
	Page<FileStoragePO> pageSearchByCondition(Integer pageNum, Integer pageSize, FileStoragePO fgetFileStoragePO);
	
	

}
