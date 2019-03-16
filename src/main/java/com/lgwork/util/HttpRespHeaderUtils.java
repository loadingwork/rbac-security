package com.lgwork.util;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.google.common.net.HttpHeaders;
import com.google.common.net.MediaType;

/**
 * 
 * http响应头
 * 
 * @author irays
 *
 */
public class HttpRespHeaderUtils {

	/**
	 * 添加图片响应头
	 * 
	 * @param response
	 */
	public static void addImageHeaders(HttpServletResponse response) {

		// 返回类型
		response.setContentType("image/png");

		response.setDateHeader("Expires", 0L);
		response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
		response.addHeader("Cache-Control", "post-check=0, pre-check=0");
		response.setHeader("Pragma", "no-cache");

		long time = System.currentTimeMillis();
		response.addHeader("Last-Modified", String.valueOf(time));
	}

	/**
	 * 添加音频
	 * 
	 * @param response
	 */
	public static void addAudioHeaders(HttpServletResponse response) {

		// 设置返回类型
		response.setContentType("audio/wave");
		response.addHeader("Cache-Control", "private,no-cache,no-store");

	}

	/**
	 * 根据后缀添加响应头
	 * @param resp
	 * @param ext
	 * @throws Exception 
	 */
	public static void addFileHeaders(HttpServletResponse response,String fileName, String ext) throws Exception {
		
		// 先处理png jpeg jpg 其他按文件下载
		
//		Content-Disposition: inline; filename=image.png
		
		if (StringUtils.isNotEmpty(ext) && ext.toLowerCase().contains("png")) {
			response.setContentType(MediaType.PNG.toString());
			response.setHeader(HttpHeaders.CONTENT_DISPOSITION, 
					String.format("inline; filename=\"%s\"", URLEncoder.encode(fileName, "UTF-8")));
			return;
		}
		if (StringUtils.isNotEmpty(ext) && 
				(ext.toLowerCase().contains("jpeg") || ext.toLowerCase().contains("jpg"))) {
			// 文件名urlEncode
			response.setHeader(HttpHeaders.CONTENT_DISPOSITION, 
					String.format("inline; filename=\"%s\"", URLEncoder.encode(fileName, "UTF-8")));
			response.setContentType(MediaType.JPEG.toString());
			return;
		}
		
		if (StringUtils.isNotEmpty(ext) && ext.toLowerCase().contains("zip")) {
			// 文件名urlEncode
			response.setHeader(HttpHeaders.CONTENT_DISPOSITION, 
					String.format("attachment; filename=\"%s\"", URLEncoder.encode(fileName, "UTF-8")));
			response.setContentType(MediaType.ZIP.toString());
			return;
		}
		
		if (StringUtils.isNotEmpty(ext) && ext.toLowerCase().contains("gzip")) {
			// 文件名urlEncode
			response.setHeader(HttpHeaders.CONTENT_DISPOSITION, 
					String.format("attachment; filename=\"%s\"", URLEncoder.encode(fileName, "UTF-8")));
			response.setContentType(MediaType.GZIP.toString());
			return;
		}
		
		// 二进制
		response.setContentType(MediaType.OCTET_STREAM.toString());
		
		// 文件名urlEncode
		response.setHeader(HttpHeaders.CONTENT_DISPOSITION, 
				String.format("attachment; filename=\"%s\"", URLEncoder.encode(fileName, "UTF-8")));
		
	}
	
	

}
