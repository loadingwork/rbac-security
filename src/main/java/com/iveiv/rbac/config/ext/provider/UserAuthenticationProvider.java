package com.iveiv.rbac.config.ext.provider;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.iveiv.rbac.config.ext.UserInfoDetails;

/**
 * 
 * 凭证:  用户名/ 手机号/ 邮箱
 * 令牌: 密码
 * 
 * 自定义令牌认证提供者
 * 
 * @author irays
 *
 */
public class UserAuthenticationProvider  extends AbstractUserDetailsAuthenticationProvider {
	
	
	/**
	 * 用户密码
	 */
	private BCryptPasswordEncoder passwordEncoder;
	/**
	 * 用户信息获取
	 */
	private UserDetailsService userDetailsService;
	

	public BCryptPasswordEncoder getPasswordEncoder() {
		return passwordEncoder;
	}

	public void setPasswordEncoder(BCryptPasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	public UserDetailsService getUserDetailsService() {
		return userDetailsService;
	}

	public void setUserDetailsService(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	
	/**
	 * 
	 * 获取从retrieveUser获取的信息验证密码 -->  userDetails => UserInfoDetails
	 * 
	 * UsernamePasswordAuthenticationFilter 拦截到的用户信息 ->   authentication => UsernamePasswordAuthenticationToken
	 * 
	 */
	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
		
		// admin 
		
		// 上一步获取的用户信息
		UserInfoDetails userInfoDetails = (UserInfoDetails)userDetails;

		if (authentication.getCredentials() == null) {
			throw new BadCredentialsException("密码不能为空");
		}
		// 前端传到后台密码
		String presentedPassword = authentication.getCredentials().toString() + userInfoDetails.getSalt();
		
		if (!passwordEncoder.matches(presentedPassword, userDetails.getPassword())) {
			
			throw new BadCredentialsException("密码错误");
		}
	
		
	}
	
	
	
	/**
	  	UsernameNotFoundException 用户找不到
		BadCredentialsException 坏的凭据
		AccountStatusException 用户状态异常它包含如下子类
		AccountExpiredException 账户过期
		LockedException  账户锁定
		DisabledException   账户不可用
		CredentialsExpiredException   证书过期
		
		父级   preAuthenticationChecks  
			- LockedException
			- DisabledException
			- AccountExpiredException
		    authenticate
		    - retrieveUser throw ->  UsernameNotFoundException  -> BadCredentialsException
		    postAuthenticationChecks
		      - CredentialsExpiredException
		    
		this additionalAuthenticationChecks 自定义
			- BadCredentialsException
		
	 */
	@Override
	protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		
		// username  可能为用户名, 手机号, 邮箱
		UserDetails loadUserByUsername = this.userDetailsService.loadUserByUsername(username);
		
		return loadUserByUsername;
	}

}
