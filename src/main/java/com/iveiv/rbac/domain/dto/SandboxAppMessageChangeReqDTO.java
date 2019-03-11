package com.iveiv.rbac.domain.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

import lombok.Getter;
import lombok.Setter;

/**
 * 沙箱消息装换
 * @author irays
 *
 */
@Setter
@Getter
public class SandboxAppMessageChangeReqDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 请求body
	 */
	private String requestBody;
	/**
	 * 请求参数
	 */
	private List<Map<String, String>> params;
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

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
