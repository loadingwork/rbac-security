package com.lgwork.sys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lgwork.base.result.BaseResult;
import com.lgwork.base.result.PageResult;
import com.lgwork.domain.po.FileStoragePO;
import com.lgwork.sys.service.FileStorageService;


/**
 * 
 * 文件控制器
 * @AuthUserInfo UserInfoDetails
 * 
 * @author irays
 *
 */
@RequestMapping("/file/storage")
@Controller
public class FileStorageController {
	
	/**
	 * 页面统一存放地址
	 */
	private  final String BASE_PATH = "/home/file/storage";
	
	/**
	 * 
	 * 文件储存服务接口
	 * 
	 */
	@Autowired
	private FileStorageService fileStorageService;
	
	/**
	 * 本地文件配置信息
	 */
//	@Autowired
//	private FileStorageProperties fileStorageProperties;
	
	/**
	 * 文件主页
	 * @return
	 */
	@GetMapping("/index.do")
	public String index() {
		return BASE_PATH + "/index";
	}
	
	
	/**
	 * 分页实现
	 * @param pageNum
	 * @param pageSize
	 * @param fgetFileStoragePO
	 * @return
	 */
	@GetMapping("/search")
	@ResponseBody
	public BaseResult<List<FileStoragePO>> searchGetSysRole(
			 @RequestParam(name="pageNum", required= false, defaultValue="1")  Integer pageNum, 
			 @RequestParam(name="pageSize", required= false, defaultValue="10")  Integer pageSize, 
			 final FileStoragePO fgetFileStoragePO
			) {
		
		// 分页获取参数
		Page<FileStoragePO> pageSearchByCondition = fileStorageService.pageSearchByCondition(pageNum, pageSize, fgetFileStoragePO);
		
		
		return new PageResult<FileStoragePO>(pageSearchByCondition);
	}
	
	
	
}
