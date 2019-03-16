package com.lgwork.sys.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.lgwork.base.result.BaseResult;
import com.lgwork.base.result.BaseResultBuilder;
import com.lgwork.config.ext.FileStorageProperties;
import com.lgwork.domain.dto.FileUploadRespDTO;
import com.lgwork.domain.po.FileStoragePO;
import com.lgwork.enums.ErrorCodeEnum;
import com.lgwork.sys.dao.FileStorageDAO;
import com.lgwork.sys.service.FilePersistLocalService;
import com.lgwork.sys.service.FileStorageService;

import lombok.extern.slf4j.Slf4j;


/**
 * 
 * 文件储存服务实现
 * 
 * @author irays
 *
 */
@Slf4j
@Service
public class FileStorageServiceImpl  implements FileStorageService {
	
	/**
	 * 文件持久化服务
	 */
	@Autowired
	private FilePersistLocalService filePersistLocalService;
	
	/**
	 * 文件持久化对象
	 */
	@Autowired
	private FileStorageDAO fileStorageDAO;
	
	/**
	 * 本地文件配置信息
	 */
	@Autowired
	private FileStorageProperties fileStorageProperties;
	

	@Transactional(rollbackFor=Exception.class,  propagation=Propagation.REQUIRED)
	@Override
	public BaseResult<FileUploadRespDTO> uploadSingleFile(MultipartFile file, String type) throws Exception {
		
		Map<String, String> secondPositions = fileStorageProperties.getSecondPositions();
		if (secondPositions == null) {
			log.debug("二级业务路径没有配置");
			throw new RuntimeException("二级业务路径没有配置");
		}
		
		// 二级路径
		final String secondPosition = secondPositions.get(type);
		if (StringUtils.isEmpty(secondPosition)) {
			log.debug("二级路径没有配置");
			return new BaseResultBuilder<FileUploadRespDTO>().fail("二级路径没有配置").build();
		}
		
		if (file == null) {
			log.debug("上传文件不存在");
			return new BaseResultBuilder<FileUploadRespDTO>().fail("上传文件不存在").build();
		}
		
		// 检测文件
		String checkFile = checkFile(file, type);
		if ("ok".equals(checkFile)) {
			// ILLEGAL_DOCUMENT_DETECTION
			return new BaseResultBuilder<FileUploadRespDTO>()
					.fail(checkFile)
					.enums(ErrorCodeEnum.ILLEGAL_DOCUMENT_DETECTION)
					.build();
		}
		
		// 执行上传
		FileUploadRespDTO handleResult = filePersistLocalService.handleSaveSingleFile(file, type);
		
		// 构建结果
		BaseResult<FileUploadRespDTO> result = 
				new BaseResultBuilder<FileUploadRespDTO>().data(handleResult).build();
		
		return result;
	}
	
	
	/**
	 * 根据业务代码检测文件
	 * 检测通过为: ok
	 * @param file
	 * @param type
	 * @return
	 */
	private String checkFile(MultipartFile file, String type) {
		
		// 暂时都当做文件处理
		
		// @TODO 文件大小检测 文件类型检测
		
		
		return "ok";
	}


	
	@Transactional(rollbackFor=Exception.class,  propagation=Propagation.REQUIRED)
	@Override
	public BaseResult<List<FileUploadRespDTO>> uploadMultipleFile(
			List<MultipartFile> files, String type) throws Exception {
		
		Map<String, String> secondPositions = fileStorageProperties.getSecondPositions();
		if (secondPositions == null) {
			log.debug("二级业务路径没有配置");
			throw new RuntimeException("二级业务路径没有配置");
		}
		
		// 二级路径
		final String secondPosition = secondPositions.get(type);
		if (StringUtils.isEmpty(secondPosition)) {
			log.debug("二级路径没有配置");
			return new BaseResultBuilder<List<FileUploadRespDTO>>().fail("二级路径没有配置").build();
		}
		
		if (files == null || files.isEmpty()) {
			log.debug("上传文件不存在");
			return new BaseResultBuilder<List<FileUploadRespDTO>>().fail("上传文件不存在").build();
		}
		
		// 声明结果
		List<FileUploadRespDTO> listResult = new ArrayList<>();
		
		for(MultipartFile file : files) {
			
			FileUploadRespDTO fileUploadRespDTO = null;
			
			// 文件检查
			// 检测文件
			String checkFile = checkFile(file, type);
			if (!"ok".equals(checkFile)) {
				fileUploadRespDTO =  new FileUploadRespDTO();
				fileUploadRespDTO.setCode("");
				fileUploadRespDTO.setUrl("");
				fileUploadRespDTO.setName(file.getOriginalFilename());
				fileUploadRespDTO.setStatus("fail");
			}
			
			try {
				if (fileUploadRespDTO == null) {
					// 没有错误信息
					// 处理文件
					fileUploadRespDTO = filePersistLocalService.handleSaveSingleFile(file, type);
				}
				
			} catch (Exception e) {
				log.debug("文件上传失败: {}", file.getOriginalFilename());
				fileUploadRespDTO =  new FileUploadRespDTO();
				fileUploadRespDTO.setCode("");
				fileUploadRespDTO.setUrl("");
				fileUploadRespDTO.setName(file.getOriginalFilename());
				fileUploadRespDTO.setStatus("fail");
			}
			
			// 添加结果
			listResult.add(fileUploadRespDTO);
		}
		
		// 构建结果
		BaseResult<List<FileUploadRespDTO>> result = 
				new BaseResultBuilder<List<FileUploadRespDTO>>().data(listResult).build();
		
		return result;
	}


	@Override
	public FileStoragePO getByFileCode(String code) {
		return fileStorageDAO.findByFileCode(code);
	}


	@Override
	public Page<FileStoragePO> pageSearchByCondition(Integer pageNum, Integer pageSize,
			FileStoragePO fgetFileStoragePO) {
		
		//分页排序
        Sort sort = new Sort(Direction.DESC, "gmtCreate");
        PageRequest pageRequest = PageRequest.of(pageNum - 1, pageSize, sort);
        
        /**
		 * 使用dsl语法查询
		 */
		Page<FileStoragePO> pagePO = fileStorageDAO.findAll(new Specification<FileStoragePO>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Predicate toPredicate(Root<FileStoragePO> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				
				List<Predicate> predicates = new ArrayList<Predicate>();
				
				// 查询条件不为空
				if(fgetFileStoragePO != null) {
					
					// 用户名模糊查询
					if(StringUtils.isNotEmpty(fgetFileStoragePO.getOriginalFilename())) {
						predicates.add(criteriaBuilder.like(root.get("original_filename").as(String.class), "%" + fgetFileStoragePO.getOriginalFilename() + "%"));
					}
					
					
				}
				
				
				Predicate[] newEmptyPredicate = new Predicate[predicates.size()];
				return criteriaBuilder.and(predicates.toArray(newEmptyPredicate));
			}
			
		}, pageRequest);
		
		// 返回结果
		return pagePO;
	}
	

}
