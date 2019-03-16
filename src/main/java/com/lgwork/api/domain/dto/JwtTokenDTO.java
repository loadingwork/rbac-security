package com.lgwork.api.domain.dto;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.lgwork.enums.TokenOptionEnum;

import lombok.Getter;
import lombok.Setter;

/**
 * token信息
 * 
 * @author irays
 *
 */
@Setter
@Getter
public class JwtTokenDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8525533756115487230L;
	/**
	 * 设备的uid
	 * 账号编码
	 */
	private String ucode;
	/**
	 *  redis中唯一id
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
	 * 操作 登录 或则 刷新
	 */
	TokenOptionEnum tokenOptionEnum;
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
}
