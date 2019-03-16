package com.lgwork.domain.po;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.lgwork.base.BasePO;
import com.lgwork.base.jpa.convert.BoolConvert;

import lombok.Getter;
import lombok.Setter;

/**
 * 扫码登录表
 * @author irays
 *
 */
@Table(name="qrcode_token_login")
@Entity
@Setter
@Getter
public class QrcodeTokenLoginPO extends BasePO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 随机token
	 */
	@Column(name="qrcode_token", length=128, unique=true, nullable=false)
	private String qrcodeToken;
	/**
	 * 客户端id
	 */
	@Column(name="client_id", length=64)
	private String clientId;
	/**
	 * 用户名
	 */
	@Column(name="username", length=128)
	private String username;
	/**
	 * 用户编码
	 */
	@Column(name="ucode", length=64)
	private String ucode;
	/**
	 * 第一次连接token
	 */
	@Column(name="link_token", length=500)
	private String linkToken;
	/**
	 * 确认token
	 */
	@Column(name="confirm_token", length=500)
	private String confirmToken;
	/**
	 * 确认后客户端token
	 */
	@Column(name="client_token", length=32)
	private String clientToken;
	
	/**
	 * 产品编码
	 */
	@Column(name="device_code", length=100)
	private String deviceCode;
	
	/**
	 * 登录时间
	 */
	@Column(name="login_time")
	private Date loginTime;
	
	/**
	 * 是否使用
	 */
	@Convert(converter=BoolConvert.class)
	@Column(name="is_used",  length=5 ,nullable=false)
	private Boolean used;

}
