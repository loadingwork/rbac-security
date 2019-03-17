package com.lgwork.sys.controller;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import com.lgwork.enums.QrcodeProtocolEnum;
import com.lgwork.sys.service.LoginService;
import com.lgwork.util.HttpRespHeaderUtils;
import com.lgwork.util.QrcodeUtil;

/**
 * 
 * 登录控制器
 * 
 * @author irays
 *
 */
@Controller
public class LoginController {
	
	/**
	 * 页面统一存放地址
	 */
//	private  final String BASE_PATH = "";
	
	/**
	 * 登录服务
	 */
	@Autowired
	private LoginService loginService;
	
	/**
	 * 
	 * var headers = {};
		headers[headerName] = token;
		stompClient.connect(headers, function(frame) {
		
		}
		
	    Static HTML  或者 ajax登录
	  	var headerName = "${_csrf.headerName}";
		var token = "${_csrf.token}";
		
		// 参数解析在org.springframework.security.web.method.annotation.CsrfTokenArgumentResolver
		
		// 使用restful api
		@RequestMapping("/csrf")
		@ResponseBody
	    public CsrfToken csrf(CsrfToken token) {
	        return token;
	    }
	    
	    <c:url var="logoutUrl" value="/logout"/>
	    <form:form action="${logoutUrl}"
	        method="post">
	    <input type="submit"
	        value="Log out" />
	    <input type="hidden"
	        name="${_csrf.parameterName}"
	        value="${_csrf.token}"/>
	    </form:form>
	 * 
	 * 去登录界面
	 * 
	 * @return
	 */
	@GetMapping(value="/login.do")
	public String loginAction(ModelMap modelMap ,String error, HttpServletRequest request) {
		
		// 获取session
		HttpSession session = request.getSession();
		
		AuthenticationException exception = 
				(AuthenticationException)session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
//		设置默认值
		modelMap.addAttribute("errmsg", "");
		
		if(StringUtils.isNotEmpty(error) 
				&& exception != null) {
			
			if(exception instanceof UsernameNotFoundException) {
				modelMap.addAttribute("errmsg", "用户名不存在");
			} else if(exception instanceof LockedException) {
				modelMap.addAttribute("errmsg", "账号被锁定, 请联系管理员");
			} else if(exception instanceof DisabledException) {
				modelMap.addAttribute("errmsg", "账号不可用, 请联系管理员");
			} else if(exception instanceof AccountExpiredException) {
				modelMap.addAttribute("errmsg", "账号过期, 请联系管理员");
			} else if(exception instanceof CredentialsExpiredException) {
				modelMap.addAttribute("errmsg", "密码过期, 请联系管理员 ");
			} else if(exception instanceof BadCredentialsException) {
				modelMap.addAttribute("errmsg", "账号或密码错误");
			} else {
//				发生未知错误
				modelMap.addAttribute("errmsg", "账号或密码错误");
			}
			
			// 设置数据为空
			session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
		}
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if(auth != null && auth.isAuthenticated()) {
			if(!(auth instanceof AnonymousAuthenticationToken)) {
				// 直接重定向到主页
				return "redirect:/home/index.do";
			}
		}
		
		return "/login/username_login";
	}
	
	
	
	/**
	 * 
	 * 二维码登录
	 * 
	 * @return
	 */
	@GetMapping(value="/qrcode.do")
	public String qrcodeLoginAction(ModelMap model) {
		
		
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if(auth != null && auth.isAuthenticated()) {
			if(!(auth instanceof AnonymousAuthenticationToken)) {
				// 直接重定向到主页
				return "redirect:/home/index.do";
			}
		}
		
		/**
		 * 获取token
		 */
		final String qrcodeToken = loginService.createQrcodeToken();
		
		// 发送到前台
		model.addAttribute("qrcodeToken", qrcodeToken);
		
		return "/login/qrcode_login";
	}
	
	
	@GetMapping(value="/qrcode/img.do")
	public void getQrcode(HttpServletResponse resp, String qrcodeToken) throws Exception {
		
		// 内容
		String content = QrcodeProtocolEnum.QR_LOGIN.value() + qrcodeToken;
		
		// 添加请求头
		HttpRespHeaderUtils.addImageHeaders(resp);
		ServletOutputStream out = resp.getOutputStream();
		
		byte[] in = QrcodeUtil.createQrcodeStream(content, 400, 400);
		
		out.write(in);
		
		out.flush();
		out.close();
		
	}
	
	
	

}
