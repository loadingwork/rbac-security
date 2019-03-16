package com.lgwork.api.domain.dto;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.lgwork.enums.TokenOptionEnum;

import lombok.Getter;
import lombok.Setter;

/**
 * 记住密码
 * 
 * @author irays
 *
 */
@Setter
@Getter
public class TokenRememberMeDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1198920311164083520L;
	/**
	 * 账号编码
	 */
	private String ucode;
	/**
	 * 储存编码
	 */
	private String pcode;
	/**
	 *  产品编号
	 */
	private String pnum;
	/**
	 * 签发时间
	 */
	private Date issuedAt;
	/**
	 * 过期时间
	 */
	private Date expiresAt;
	/**
	 * 有效时间
	 */
	private Date invalidAt;
	/**
	 * 操作
	 */
	private TokenOptionEnum tokenOptionEnum;
	
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
