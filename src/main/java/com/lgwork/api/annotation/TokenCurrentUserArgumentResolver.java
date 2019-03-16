package com.lgwork.api.annotation;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.lgwork.api.config.JwtTokenHelper;
import com.lgwork.api.config.TokenAuthProperties;
import com.lgwork.api.domain.dto.JwtTokenDTO;
import com.lgwork.api.domain.dto.UserInfoDTO;
import com.lgwork.api.service.PersistentTokenService;

/**
 * @TokenCurrentUser 注解解析器
 * 
 * @author irays
 *
 */
public class TokenCurrentUserArgumentResolver implements HandlerMethodArgumentResolver {

	/**
	 * 配置文件
	 */
	@Autowired
	private TokenAuthProperties tokenAuthProperties;

	/**
	 * token加密辅助
	 */
	@Autowired
	private JwtTokenHelper jwtTokenHelper;

	/**
	 * token持久化实现, (推荐使用redis储存)
	 */
	@Resource(name = "persistentTokenRedisServiceImpl")
	private PersistentTokenService persistentTokenService;

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		// 判断是否支持要转换的参数类型
		return parameter.hasParameterAnnotation(TokenCurrentUser.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

		// 获取token
		String token = webRequest.getHeader(tokenAuthProperties.getTokenParam());

		// 数据为空
		if (StringUtils.isEmpty(token)) {
			// 从参数中获取
			token = webRequest.getParameter("token");
			if (StringUtils.isEmpty(token)) {
				return null;
			}
		}

		// token解码 (这里没有token没有验证, 一定要在登录拦截器下使用)
		JwtTokenDTO jwtTokenDTO = jwtTokenHelper.jwtDecode(token);

		// 获取redis中数据
		UserInfoDTO userInfoDTO = persistentTokenService.getByPcode(jwtTokenDTO.getPcode());

		// 返回用户信息
		return userInfoDTO;
	}

}
