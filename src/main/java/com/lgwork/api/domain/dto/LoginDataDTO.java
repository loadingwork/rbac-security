package com.lgwork.api.domain.dto;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

import lombok.Getter;
import lombok.Setter;

/**
 * 登录请求
 * @author irays
 *
 */
@Setter
@Getter
public class LoginDataDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6568793519539896108L;
	/**
	 * 令牌: 手机号, 用户名, 邮箱
	 */
	private String username;
	/**
	 * 凭证: 密码
	 */
	private String password;

	/**
	 * 只能是1或者0
	 * 
	 * 1 表示是 0 否
	 * 
	 */
	private Integer rememberMe;
	/**
	 * 系统编码
	 */
	private String scode;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
	

}
