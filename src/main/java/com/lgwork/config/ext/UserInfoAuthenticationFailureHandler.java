package com.lgwork.config.ext;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

/**
 * 
 * 自定义登录失败处理
 * 
 * @author irays
 *
 */
public class UserInfoAuthenticationFailureHandler implements AuthenticationFailureHandler {
	
	private String defaultFailureUrl;
	
	public String getDefaultFailureUrl() {
		return defaultFailureUrl;
	}

	public void setDefaultFailureUrl(String defaultFailureUrl) {
		this.defaultFailureUrl = defaultFailureUrl;
	}


	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		
		System.err.println(defaultFailureUrl + 1111);
		
		// 请求重定向
		request.getRequestDispatcher(defaultFailureUrl + 1111)
		.forward(request, response);
		
	}

}
