package com.iveiv.rbac.api.config;

import java.io.Serializable;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * token配置
 * 
 * @author irays
 *
 */
@Setter
@Getter
@ConfigurationProperties("app.token.auth")
public class TokenAuthProperties implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 开发模式(暂时不使用)
	 * 
	 * pro test dev
	 * 
	 */
	private String mode;
	/**
	 * 加密key集合
	 */
	private String tokenSecretKey;
	/**
	 * 有效时间(单位秒)
	 */
	private int expiresIn;
	/**
	 * 失效时间(单位秒)
	 */
	private int invalidIn;
	/**
	 * token值名称
	 */
	private String tokenParam;
	/**
	 * token过期参数
	 */
	private String tokenExpiresParam;

	/**
	 * 记住我加密key集合
	 */
	private String rememberMeSecretKey;

	/**
	 * 记住我密码有效时间(单位秒)
	 */
	private int rememberMeExpiresIn;
	/**
	 * 记住密码失效时间(单位秒)
	 */
	private int rememberMeInvalidIn;
	/**
	 * 记住密码名称
	 */
	private String rememberMeParam;

	/**
	 * 请求随机数
	 */
	private String tokenRandomStr;
	/**
	 * 签发机构
	 */
	private String issuer;

}
