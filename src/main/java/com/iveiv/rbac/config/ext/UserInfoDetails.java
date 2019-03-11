package com.iveiv.rbac.config.ext;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 * 
 * 自定义用户信息
 * 
 * @author irays
 *
 */
public class UserInfoDetails extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7628905026451128635L;
	
	/**
	 * 密码的盐值
	 */
	private String salt;
	

	public UserInfoDetails(String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
	}
	
	public UserInfoDetails(String username, String password,
			Collection<? extends GrantedAuthority> authorities, String salt) {
		super(username, password, authorities);
		this.salt = salt;
	}


	public String getSalt() {
		return salt;
	}


	public void setSalt(String salt) {
		this.salt = salt;
	}
	
	
	/**
	 * 登录成功以后清除敏感信息
	 */
	@Override
	public void eraseCredentials() {
		super.eraseCredentials();
		this.salt = null;
	}
	
	
	

}
