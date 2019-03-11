package com.iveiv.rbac.api.domain.dto;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * 登录成功信息
 * 
 * @author irays
 *
 */
@Getter
@Setter
public class LoginSuccessDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9055057015769184259L;
	/**
	 * 登录成功事件
	 */
	private Date loginTime;
	/**
	 * 用户信息
	 */
	private UserInfoDTO user;
	
	/**
	 * 用户登录凭证信息
	 */
	private TokenSessionDTO session;
	
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	

}
