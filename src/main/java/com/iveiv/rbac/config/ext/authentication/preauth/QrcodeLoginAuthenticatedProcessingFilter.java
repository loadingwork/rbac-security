package com.iveiv.rbac.config.ext.authentication.preauth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.iveiv.rbac.sys.service.LoginService;


/**
 *   扫码用户登录
 * @author irays
 *
 */
public class QrcodeLoginAuthenticatedProcessingFilter extends AbstractAuthenticationProcessingFilter {
	
	
	public QrcodeLoginAuthenticatedProcessingFilter() {
		super(new AntPathRequestMatcher("/login/qrcode.do", "POST"));
	}
	/**
	 * 登录服务
	 */
	@Autowired
	private LoginService loginService;
	
	/**
	 * 登录token
	 */
	private final String QRCODE_TOKEN = "qrcode_token";
	/**
	 * 客户端id
	 */
	private final String CLIENT_ID = "client_id";
	/**
	 * 确认token
	 */
	private final String CLIENT_TOKEN = "client_token";
	
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		
		
		// 拦截指定url
		
		// 获取参数
		String qrcodeToken = request.getParameter(QRCODE_TOKEN);
		String clientId = request.getParameter(CLIENT_ID);
		String clientToken = request.getParameter(CLIENT_TOKEN);
		
		// 成功返回用户名
		String principal = loginService.extractUsernameByQrcodeToken(qrcodeToken, clientId, clientToken);
		
		
		
		PreAuthenticatedAuthenticationToken authRequest = new PreAuthenticatedAuthenticationToken(
				principal, "N/A");
		
		return authRequest;
	}
	
	
	
	
	
	

}
