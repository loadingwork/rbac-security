package com.iveiv.rbac.domain.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 文件上传返回对象
 * @author irays
 *
 */
@Setter
@Getter
public class FileUploadRespDTO {
	
	/**
	 * 编码
	 */
	private String code;
	/**
	 * 上传访问地址
	 */
	private String url;
	/**
	 * 上传文件名
	 */
	private String name;
	/**
	 * success  成功
	 * fail  失败
	 * uploading 上传中
	 * 异步时使用
	 * 上传我文件状态
	 */
	private String status;

}
