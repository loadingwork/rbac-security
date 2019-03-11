package com.iveiv.rbac.api.domain.dto;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

import lombok.Getter;
import lombok.Setter;

/**
 * 用户信息
 * @author irays
 *
 */
@Setter
@Getter
public class UserInfoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2630081003826125826L;
	/**
	 * 账号编码
	 */
	private String ucode;
	/**
	 *  产品编号
	 */
	private String deviceCode;

	/**
	 * 账号
	 */
	private String username;
	/**
	 * 昵称
	 */
	private String nickname;
	
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
