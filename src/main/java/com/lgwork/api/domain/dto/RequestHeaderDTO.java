package com.lgwork.api.domain.dto;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * 基本请求头模型
 * 
 * @author irays
 *
 */
@Setter
@Getter
public class RequestHeaderDTO implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6694605753345057522L;

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
	 * device-code
	 * 
	 * 简写   pnum
	 * 
	 * 产品编号 1010
	 * 
	 */
	private String deviceCode;

	/**
	 * api-version
	 * 
	 * API版本
	 */
	private String apiVersion;
	
	
	/**
	 * 获取请求
	 * @param req
	 * @return
	 */
	public static RequestHeaderDTO headers(HttpServletRequest  req) {
		// 设备唯一udid
		String deviceUdid = req.getHeader("device-udid");
		// 设备类型
		String deviceClient = req.getHeader("device-client");
		// 产品编号
		String deviceCode = req.getHeader("device-code");
		// api版本
		String apiVersion = req.getHeader("api-version");
		
		if (StringUtils.isAnyEmpty(deviceUdid, deviceClient, deviceCode)) {
			return null;
		}
		
		if (StringUtils.isEmpty(apiVersion)) {
			apiVersion = "v1";
		}
		
		RequestHeaderDTO requestHeaderDTO = new RequestHeaderDTO();
		
		requestHeaderDTO.setDeviceUdid(deviceUdid);
		requestHeaderDTO.setDeviceClient(deviceClient);
		requestHeaderDTO.setDeviceCode(deviceCode);
		requestHeaderDTO.setApiVersion(apiVersion);
		
		return requestHeaderDTO;
	}
	

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	

}
