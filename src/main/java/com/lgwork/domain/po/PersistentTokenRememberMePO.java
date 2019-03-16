package com.lgwork.domain.po;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.lgwork.base.BasePO;

import lombok.Getter;
import lombok.Setter;


/**
 * 
 * @author irays
 *
 */
@Table(name = "persistent_token_remember_me")
@Entity
@Setter
@Getter
public class PersistentTokenRememberMePO extends BasePO {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2580748596554110406L;

	/**
	 * 设备唯一的udid
	 */
	@Column(name = "device_udid", length = 128)
	private String deviceUdid;
	/**
	 * 设备类型
	 */
	@Column(name = "device_client", length = 32)
	private String deviceClient;
	/**
	 * 产品编码
	 */
	@Column(name = "device_code", length = 32)
	private String deviceCode;
	/**
	 * API版本
	 */
	@Column(name = "api_version", length = 32)
	private String apiVersion;
	/**
	 * 记住登录
	 * 
	 * 记录ucode
	 * 
	 */
	@Column(name = "remember_me_value", length = 2048)
	private String rememberMeValue;
	/**
	 * 失效期限
	 * 
	 * 这个是真实有效的过期 配置文件中 的过期代表刷新token过期时间
	 * 
	 */
	@Column(name = "invalid_in")
	private Integer invalidIn;
	/**
	 * 有效时间
	 */
	@Column(name = "invalid_at")
	private Date invalidAt;
	/**
	 * 过期时间
	 */
	@Column(name = "expires_at")
	private Date expiresAt;
	/**
	 * 最后登录时间
	 */
	@Column(name = "last_login_time")
	private Date lastLoginTime;
	/**
	 * 账号编码
	 */
	@Column(name = "ucode", length = 64)
	private String ucode;
	/**
	 * 唯一识别
	 */
	@Column(name = "pcode", length = 64, unique=true)
	private String pcode;
	/**
	 * 用户名
	 */
	@Column(name = "username", length = 100)
	private String username;

	
	

}
