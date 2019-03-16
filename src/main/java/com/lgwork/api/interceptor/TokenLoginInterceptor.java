package com.lgwork.api.interceptor;

import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.lgwork.api.config.JwtTokenHelper;
import com.lgwork.api.config.TokenAuthProperties;
import com.lgwork.api.domain.dto.JwtTokenDTO;
import com.lgwork.api.domain.dto.RequestHeaderDTO;
import com.lgwork.api.domain.dto.UserInfoDTO;
import com.lgwork.api.exceptions.BizException;
import com.lgwork.api.service.PersistentTokenService;
import com.lgwork.base.result.BaseResult;
import com.lgwork.base.result.BaseResultBuilder;
import com.lgwork.util.JsonBodyUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * 登录拦截器
 * 
 * @author irays
 *
 */
@Slf4j
public class TokenLoginInterceptor extends HandlerInterceptorAdapter {
	
	/**
	 * token加密辅助
	 */
	@Autowired
	private JwtTokenHelper jwtTokenHelper; 
	
	/**
	 * token持久化实现, (推荐使用redis储存)
	 */
	@Resource(name="persistentTokenRedisServiceImpl")
	private PersistentTokenService persistentTokenService;
	
	
	/**
	 * 配置文件
	 */
	@Autowired
	private TokenAuthProperties tokenAuthProperties;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		
		// 解码请求
		RequestHeaderDTO requestHeaderDTO = RequestHeaderDTO.headers(request);
		
		if(requestHeaderDTO == null) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "请求头参数异常");
			return false;
		}
		
		// 没有过期
		response.setHeader(tokenAuthProperties.getTokenExpiresParam(), "N");
		
		
		String authToken = request.getHeader(tokenAuthProperties.getTokenParam());
		
		if (StringUtils.isEmpty(authToken)) {
			// 从参数中获取
			authToken = request.getParameter("token");
			
			if(StringUtils.isEmpty(authToken)) {
				// 发送401
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "token不能为空");
				return false;
			}
		}
		
		// 解密token
		
		try {
			
			JwtTokenDTO jwtVerifyDecrypt =  jwtTokenHelper.jwtVerifyDecrypt(authToken);
			
			if(jwtVerifyDecrypt != null) {
				
				// 根据pcode获取数据
				UserInfoDTO userInfoDTO = persistentTokenService.getByPcode(jwtVerifyDecrypt.getPcode());
				
				if(userInfoDTO != null) {
					// 通过
					return true;
				}
			}
			
		} catch (BizException e) {
			
			if(BizException.TOKEN_ALREADY_EXPIRE.getCode() == e.getCode()) {
				// 在response中发出token过期刷新
				response.setHeader(tokenAuthProperties.getTokenExpiresParam(), "Y");
				return true;
			} else {
				// 无效token
				// 不通过
				BaseResult<String> result = new BaseResultBuilder<String>().invalidToken().build();
				PrintWriter writer = response.getWriter();
				writer.write(JsonBodyUtils.toJSONString(result));
				writer.flush();
				return false;
			}
			
		} catch (Exception e) {
			log.debug("用户登录未处理异常: {}", e.getMessage());
		}
		
		// 不通过
		BaseResult<String> result = new BaseResultBuilder<String>().syserr().build();
		PrintWriter writer = response.getWriter();
		writer.write(JsonBodyUtils.toJSONString(result));
		writer.flush();
		return false;
	}
	
	
	

}
