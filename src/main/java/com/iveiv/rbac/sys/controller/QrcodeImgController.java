package com.iveiv.rbac.sys.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.iveiv.rbac.base.result.BaseResult;
import com.iveiv.rbac.config.ext.FileStorageProperties;
import com.iveiv.rbac.domain.dto.QrcodeImgRespDTO;
import com.iveiv.rbac.domain.po.QrcodeImgPO;
import com.iveiv.rbac.sys.service.QrcodeImgService;
import com.iveiv.rbac.util.HttpRespHeaderUtils;
import com.iveiv.rbac.util.QrcodeUtil;

import lombok.extern.slf4j.Slf4j;


/**
 * 二维码控制器
 * @author irays
 *
 */
@Slf4j
@RequestMapping("/qrcodeimg")
@Controller
public class QrcodeImgController {
	
	
	/**
	 * 二维码服务接口
	 */
	@Autowired
	private QrcodeImgService qrcodeImgService;
	
	/**
	 * 文件配置
	 */
	@Autowired
	private FileStorageProperties fileStorageProperties;
	
	/**
	 * 根据内容生成指定二维码
	 * @param resp  响应
	 * @param content  内容
	 * @param width  宽度
	 * @param height  高度
	 * @throws Exception  
	 */
	@RequestMapping(value="/img")
	public void getQrcodeImg(HttpServletResponse resp,
		 @RequestParam(defaultValue="404") String content, 
		 @RequestParam(defaultValue="400") Integer width, 
		 @RequestParam(defaultValue="400") Integer height
		 ) throws Exception {
		
		// 添加请求头
		HttpRespHeaderUtils.addImageHeaders(resp);
		// 获取输出流
		ServletOutputStream out = resp.getOutputStream();
		
		// 关闭
		byte[] in = QrcodeUtil.createQrcodeStream(content, width, height);
		
		out.write(in);
		
		// 关闭流
		out.flush();
		out.close();
		
	}
	
	
	/**
	 *  生成二维码
	 * @param resp
	 * @param content
	 * @param width
	 * @param height
	 * @throws Exception
	 */
	@RequestMapping(value="/code")
	@ResponseBody
	public BaseResult<QrcodeImgRespDTO> getQrcode(HttpServletResponse resp,
		 @RequestParam(defaultValue="404") String content, 
		 @RequestParam(defaultValue="400") Integer width, 
		 @RequestParam(defaultValue="400") Integer height
		 ) throws Exception {
	
		// 保存二维码, 并获取二维码
		BaseResult<QrcodeImgRespDTO> result = 
				qrcodeImgService.saveQrcodeImgPO(width, height, content);
		
		return result;
	}
	
	
	
	
	/**
	 * 获取文件流
	 * @param resp
	 * @param fileKey  文件key
	 * @throws IOException
	 */
	@RequestMapping(value="/stream/**/{fileKey}")
	public void getImgStream(HttpServletResponse resp,
			@PathVariable("fileKey") String fileKey) throws IOException {
		
		// 添加请求头
		HttpRespHeaderUtils.addImageHeaders(resp);
		
		// 获取输出流
		ServletOutputStream output = resp.getOutputStream();
		
		String filePath = "";
		try {
			
			// 获取code
			String code = fileKey.substring((fileKey.lastIndexOf("/") + 1),fileKey.lastIndexOf(".png"));
			
			// 根据code获取
			QrcodeImgPO qrcodeImgPO = qrcodeImgService.findBycode(code);
			
			if (qrcodeImgPO != null) {
				
				// 获取系统文件
				final String qrcodeRoot = fileStorageProperties.getQrcodeRoot();
				
			    filePath = qrcodeRoot + qrcodeImgPO.getFileKey();
				
				File file = new File(filePath);
				
				if (file != null && file.exists()) {
					
					BufferedImage bimg = ImageIO.read(file);
					
					ImageIO.write(bimg, "png", output);
					
					bimg.flush();

					output.close();
					return;
				}
			}
			
		} catch (Exception e) {
			log.debug("二维码不存在: {}" + filePath);
		}
		
		
		// 404处理
		InputStream in = 
				QrcodeImgController.class.getClass().getResourceAsStream("/static/images/qrcode404.png");
		
		byte[] inByte = new byte[in.available()];
		
		// 文件不大, 一次获取
		in.read(inByte);
		
		output.write(inByte);
		// 关闭流
		output.flush();
		output.close();
	}
	
	
	
	
	

}
