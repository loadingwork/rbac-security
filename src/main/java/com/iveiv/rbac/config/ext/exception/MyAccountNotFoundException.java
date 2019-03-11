package com.iveiv.rbac.config.ext.exception;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * 
 * 账号不存在
 * 
 * AccountNotFoundException 账号不存在
 * 
 * UsernameNotFoundException 账户不存在 
 * 
 * 注: 为了以后多个系统联合, 多个系统下的账户必须开通
 * 
 * @author irays
 *
 */
public class MyAccountNotFoundException extends UsernameNotFoundException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4355895243518299048L;

	public MyAccountNotFoundException(String msg) {
		super(msg);
	}
	
	public MyAccountNotFoundException(String msg, Throwable t) {
		super(msg, t);
	}

	
	
}
