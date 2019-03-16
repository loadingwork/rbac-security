package com.lgwork.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.lgwork.api.event.TokenAuthEventPublisher;

/**
 * 
 * token配置
 * 
 * @author irays
 *
 */
@Configuration
public class TokenAuthConfig {
	
	
	/**
	 * token 配置参数
	 * @return
	 */
	@Bean
	public TokenAuthProperties tokenAuthProperties() {
		return new TokenAuthProperties();
	}
	
	
	
	/**
	 * 自定义发布事件发布
	 * @return
	 */
	@Bean(name="tokenAuthEventPublisher")
	public TokenAuthEventPublisher tokenAuthEventPublisher() {
		return new TokenAuthEventPublisher();
	}
	
	
	/**
	 * token辅助类
	 * @return
	 */
	@Bean
	public JwtTokenHelper jwtTokenHelper() {
		return new JwtTokenHelper(tokenAuthProperties());
	}
	
	
	/**
	 * 记住我信息加密辅助类
	 * @return
	 */
	@Bean
	public TokenRememberMeHelper tokenRememberMeHelper() {
		return new TokenRememberMeHelper(tokenAuthProperties());
	}

}
