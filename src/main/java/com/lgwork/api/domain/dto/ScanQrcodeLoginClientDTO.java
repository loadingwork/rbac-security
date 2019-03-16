package com.lgwork.api.domain.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;

import lombok.Getter;
import lombok.Setter;

/**
 * 客户端消息
 * @author irays
 *
 */
@Setter
@Getter
public class ScanQrcodeLoginClientDTO {
	/**
	 * 客户端id
	 * 
	 * 状态为scan_ok
	 * 才会有值
	 */
	private String clientId;
	/**
	 * 用户名
	 */
	private String username;
	
	/**
	 * 状态
	 * 
	 * 扫码成功
	 * scan_ok
	 * 用户确认
	 * user_ok
	 * 
	 */
	private String status;
	
	/**
	 * 状态为user_ok才会有值
	 */
	private String clientToken;
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
