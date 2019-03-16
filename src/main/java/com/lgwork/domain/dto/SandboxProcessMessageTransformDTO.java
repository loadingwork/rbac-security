package com.lgwork.domain.dto;

import java.io.Serializable;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

import lombok.Getter;
import lombok.Setter;

/**
 * 消息转换
 * @author irays
 *
 */
@Setter
@Getter
public class SandboxProcessMessageTransformDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4505827024635638361L;

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
	 * 请求参数
	 */
	private Map<String, String> params;

	
	
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
