package com.iveiv.rbac.domain.dto;

import java.io.Serializable;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

import lombok.Getter;
import lombok.Setter;


/**
 * 
 * @author irays
 *
 */
@Setter
@Getter
public class SandboxAppReqDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3332130936213200215L;

	/**
	 * 访问方式
	 */
	private String method;

	/**
	 * 请求url
	 */
	private String requestURL;

	/**
	 * 请求头
	 */
	private Map<String, String> headers;

	/**
	 * 参数
	 */
	private Map<String, String> params;

	/**
	 * device-udid 设备唯一ID
	 */
	private String deviceUdid;

	/**
	 * device-client
	 * 
	 * android 安卓 iphone 苹果 h5 移动端 wp 微软wp wx 微信
	 * 
	 * 
	 * 设备客户端类型
	 * 
	 */
	private String deviceClient;

	/**
	 * app-version
	 * 
	 * app版本
	 * 
	 */
	private String appVersion;
	
	/**
	 * 请求body
	 */
	private String requestBody;

	
	
	
	public String defaultdAppKey() {
		if(appVersion == null) {
			return deviceClient + "_default";
		}
		return deviceClient + "_" + appVersion;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
