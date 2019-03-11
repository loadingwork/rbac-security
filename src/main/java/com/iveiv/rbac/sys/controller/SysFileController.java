package com.iveiv.rbac.sys.controller;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.iveiv.rbac.base.result.BaseResult;
import com.iveiv.rbac.config.ext.FileStorageProperties;
import com.iveiv.rbac.domain.dto.FileUploadRespDTO;
import com.iveiv.rbac.domain.po.FileStoragePO;
import com.iveiv.rbac.sys.service.FileStorageService;
import com.iveiv.rbac.util.HttpRespHeaderUtils;

/**
 * 文件控制器
 * 
 * 这个控制器主要对外服务
 * 
 * @author irays
 *
 */
@RequestMapping("/sysfile")
@Controller
public class SysFileController {
	
	
	
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
	@Autowired
	private FileStorageProperties fileStorageProperties;
	
	
	
	/**
	 * 上传单个文件
	 * enctype="multipart/form-data"
	 * @param req   请求
	 * @param name  上传文件名称
	 * @param code  业务代码(文件自定义处理)  默认就是file
	 * @throws Exception 
	 */
	@RequestMapping("/single/{name}/{code}")
	@ResponseBody
	public BaseResult<FileUploadRespDTO> uploadSingleFile(MultipartHttpServletRequest req, 
			@PathVariable("name") String name,
			@PathVariable("code") String code) throws Exception {
		
		// 获取文件
		MultipartFile file = req.getFile(name);
		
		// 上传单个文件
		BaseResult<FileUploadRespDTO> result = 
				fileStorageService.uploadSingleFile(file, code);
		
		return result;
	}
	
	
	/**
	 * 多个文件传
	 * @param req  请求
	 * @param code  业务代码
	 * @throws Exception 
	 */
	
	/**
	 * 多个文件上传
	 * enctype="multipart/form-data"
	 * @param req  请求
	 * @param name  自定文件名
	 * @param code  业务代码
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/multiple/{name}/{code}")
	@ResponseBody
	public BaseResult<List<FileUploadRespDTO>>  uploadMultipleFile(MultipartHttpServletRequest req,
			@PathVariable("name") String name,
			@PathVariable("code") String code) throws Exception {
		
		// 获取所有上传的文件
		List<MultipartFile> files = req.getFiles(name);
		
		BaseResult<List<FileUploadRespDTO>> result = 
				fileStorageService.uploadMultipleFile(files, code);
		
		return result;
	}
	
	
	
	/**
	 * 下载文件
	 * @param code
	 * @param ext
	 */
	@RequestMapping("/stream/**/{fileKey}")
	public void downloadSingleFile( 
			HttpServletResponse resp,
			@PathVariable("fileKey") String fileKey ) throws Exception {
		
		ServletOutputStream output = resp.getOutputStream();
		
		if (StringUtils.isNotEmpty(fileKey)) {
			try {
				
				String code = fileKey.substring(fileKey.lastIndexOf("/") + 1);
//				fileKey.lastIndexOf(".")
				String ext = "";
				int hasExt = code.lastIndexOf(".");
				if (hasExt > -1) {
					// 有后缀
					code = code.substring(0, hasExt);
					ext = code.substring(hasExt + 1);
				}
				
				// 根据后缀添加不同的响应头
				// 添加请求头
				HttpRespHeaderUtils.addFileHeaders(resp, code, ext);
				
				// 根据code获取文件
				FileStoragePO fileStoragePO = fileStorageService.getByFileCode(code);
				if (fileStoragePO != null) {
					String fileAbsolutePath = fileStorageProperties.getFileRoot() + fileStoragePO.getFileKey();
					
					FileInputStream in = new FileInputStream(fileAbsolutePath);
					
					BufferedInputStream bufferInput = new BufferedInputStream(in);
					
					// 下载显示
					IOUtils.copy(bufferInput, output);
					
					// 关闭流
					bufferInput.close();
					in.close();

					return;
				}
			} catch (Exception e) {
				Log.error(e.getMessage(), e);
			}
			
		}
		
		// 返回404页面
		HttpRespHeaderUtils.addFileHeaders(resp, "file404.png", "png");
		
		// 404处理
		InputStream in = 
				SysFileController.class.getClass().getResourceAsStream("/static/images/file404.png");
		
		byte[] inByte = new byte[in.available()];
		
		// 文件不大, 一次获取
		in.read(inByte);
		
		output.write(inByte);
		// 关闭流
		output.flush();
		output.close();
		
	}
	


	
	

}
