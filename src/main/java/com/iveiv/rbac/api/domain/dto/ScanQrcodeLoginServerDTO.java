package com.iveiv.rbac.api.domain.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;

import lombok.Getter;
import lombok.Setter;

/**
 * 后台消息
 * @author irays
 *
 */
@Setter
@Getter
public class ScanQrcodeLoginServerDTO {
	
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
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
