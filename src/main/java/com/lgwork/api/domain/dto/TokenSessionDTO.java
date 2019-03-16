package com.lgwork.api.domain.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;

import lombok.Getter;
import lombok.Setter;

/**
 * 用户登录成功凭证
 * @author irays
 *
 */
@Setter
@Getter
public class TokenSessionDTO {
	
	/**
	 * 登录的key
	 */
	private String token;
	/**
	 * 记住登录密钥信息
	 */
	private String rememberMeValue;
	
	/**
	 * 用户编码
	 */
	private String ucode;
	
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
