package com.iveiv.rbac.config.ext;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 用户密码验证工具类
 * @author irays
 *
 */
public class PasswordHelper {
	
	/**
	 * 用户密码加密模块
	 */
	private PasswordEncoder passwordEncoder;
	
	
	public PasswordHelper(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}
	
	
	/**
	 * 加密
	 * @param rawPassword  原始密码
	 * @param salt  盐值
	 * @return
	 */
	public  String encode(String rawPassword, String salt) {
		return passwordEncoder.encode(rawPassword + salt);
	}
	
	/**
	 * 密码匹配
	 * @param rawPassword  原始密码
	 * @param encodedPassword  数据中密码
	 * @param salt  盐值
	 * @return
	 */
	public  boolean matches(String rawPassword, String encodedPassword, String salt) {
		return passwordEncoder.matches(rawPassword + salt, encodedPassword);
	}
	

}
