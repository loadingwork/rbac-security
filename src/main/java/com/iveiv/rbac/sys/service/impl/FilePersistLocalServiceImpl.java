package com.iveiv.rbac.sys.service.impl;

import java.io.File;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.iveiv.rbac.config.ext.FileStorageProperties;
import com.iveiv.rbac.domain.dto.FileUploadRespDTO;
import com.iveiv.rbac.domain.po.FileStoragePO;
import com.iveiv.rbac.sys.dao.FileStorageDAO;
import com.iveiv.rbac.sys.service.FilePersistLocalService;
import com.iveiv.rbac.util.UuidUtil;


/**
 * 持久化文件到本地
 * 注: 这个类只处理文件
 * @author irays
 *
 */
@Service
public class FilePersistLocalServiceImpl implements FilePersistLocalService {
	
	
	/**
	 * 本地文件配置信息
	 */
	@Autowired
	private FileStorageProperties fileStorageProperties;
	
	/**
	 * 文件持久化对象
	 */
	@Autowired
	private FileStorageDAO fileStorageDAO;

	
	/**
	 * 每次开启一个新事物
	 */
	@Transactional(rollbackFor=Exception.class,  propagation=Propagation.REQUIRES_NEW)
	@Override
	public FileUploadRespDTO handleSaveSingleFile(MultipartFile file, String type) throws Exception {
		
		
		String originalFilename = file.getOriginalFilename();
		// 获取问价后缀
		String ext = FilenameUtils.getExtension(originalFilename);
		
		// --- 常量
		final String fileCode = UuidUtil.uuid32();
		String storageName = fileCode;
		
		if (StringUtils.isNotEmpty(ext)) {
			// 转小写
			ext = ext.toLowerCase();
			storageName = storageName + "." + ext;
		} else {
			// 默认值
			ext = "";
		}
		// 获取文件key
		final String fileKey = fileStorageProperties.localFileRelativeFileKey(type, ext, storageName);
		file.getOriginalFilename();
		
		FileStoragePO fileStoragePO = new FileStoragePO();
		long size = file.getSize();
		
		// 初始化
//		BasePO.initData(fileStoragePO);
		fileStoragePO.setFileCode(fileCode);
		fileStoragePO.setFileKey(fileKey);
		fileStoragePO.setDescription("");
		fileStoragePO.setOriginalFilename(originalFilename);
		fileStoragePO.setExt(ext);
		fileStoragePO.setFileSize(size);
		fileStoragePO.setSizeUnit("B");
		fileStoragePO.setStorageName(storageName);
		// 默认过期1小时
		DateTime dt = new DateTime();
		// 增加一个小时
		DateTime tempExpiresAt = dt.plusHours(1);
		fileStoragePO.setTempExpiresAt(tempExpiresAt.toDate());
		fileStoragePO.setStorageType(type);
		fileStoragePO.setCreateBy("");
		fileStoragePO.setEnabled(false);
		
		fileStorageDAO.save(fileStoragePO);
		
		// 获取文件绝对路径
		final String fileAbsolutePath = fileStorageProperties.getFileRoot() + fileKey;
		
		File fileAbsolutePathFile = new File(fileAbsolutePath);
		
		if (!fileAbsolutePathFile.exists()) {
			File parentFile = fileAbsolutePathFile.getParentFile();
			// 文件不存在
			if (parentFile !=  null &&
					!parentFile.exists()) {
				// 创建路径
				parentFile.mkdirs();
			}
		}
		
		// 获取输入流
		InputStream inputStream = file.getInputStream();
		
		// 保存文件
		FileUtils.copyInputStreamToFile(inputStream, fileAbsolutePathFile);
		
		FileUploadRespDTO fileUploadRespDTO = new FileUploadRespDTO();
		
		// 设置访问路径  root + {code}.ext
		String url = fileStorageProperties.getFileUrl() + fileKey;
		
		fileUploadRespDTO.setCode(fileCode);
		fileUploadRespDTO.setUrl(url);
		fileUploadRespDTO.setName(originalFilename);
		fileUploadRespDTO.setStatus("ok");
		
		return fileUploadRespDTO;
	}

	
	
	
	
	
	

}
