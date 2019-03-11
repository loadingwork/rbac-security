package com.iveiv.rbac.api.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.iveiv.rbac.api.domain.dto.LoginSuccessDTO;
import com.iveiv.rbac.api.domain.dto.RequestHeaderDTO;
import com.iveiv.rbac.api.domain.dto.ScanQrcodeLoginServerDTO;
import com.iveiv.rbac.api.exceptions.BizException;
import com.iveiv.rbac.api.service.TokenAuthService;
import com.iveiv.rbac.base.result.BaseResult;
import com.iveiv.rbac.base.result.BaseResultBuilder;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 
 * token 认证
 * 
 * 存储信任
 * 验证信任
 * 
 * authorization
 * 
 * @TODO  处理掉所有登录抛出异常
 * 
 * @author irays
 *
 */
@Api(tags= {"用户api认证"})
@Slf4j
@RequestMapping("/api/auth/")
@RestController
public class TokenAuthController {
	
	/**
	 * token服务实现
	 */
	@Autowired
	private TokenAuthService tokenAuthService;
	
	
	/**
	 * 测试使用
	 * @return
	 */
	@ApiOperation(value="服务器连接测试接口")
	@GetMapping("/test")
	public String test() {
		return "ok";
	}
	
	
	/**
	 * 
	 *  用户登录
	 * 
	 * @param username  账号/手机号/邮箱
	 * @param password  密码
	 * @param rememberMe 记住密码
	 */
	@ApiOperation(value = "用户与密码登录", notes="用户与密码登录获取token", 
			responseReference="com.iveiv.rbac.base.result.BaseResult<LoginSuccessDTO>", httpMethod="POST")
	@ApiResponses({
		@ApiResponse(code=200, message = "成功", reference="com.iveiv.rbac.base.result.BaseResult<LoginSuccessDTO>")
	})
	@PostMapping("/login")
	public ResponseEntity<BaseResult<LoginSuccessDTO>> loginByUsername(
			@RequestParam("username") final String username, 
			@RequestParam("password") final String password, 
			@RequestParam(name="rememberMe", required=false, defaultValue="false") boolean rememberMe,
			HttpServletRequest req) {
		
		// 获取请求头
		 RequestHeaderDTO headers = RequestHeaderDTO.headers(req);
		
		if(headers == null) {
			// 发出401
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		try {
			
			// 登录
			BaseResult<LoginSuccessDTO> login = tokenAuthService.login(username, password, rememberMe, headers);
			
			return ResponseEntity.ok(login);
		} catch (BizException e) {
			
			// 打印日记
			log.debug("用户登录业务: {}", e.getMsg());
			
			return ResponseEntity.ok(
					new BaseResultBuilder<LoginSuccessDTO>().fail(e.getMsg()).build());
			
		} catch (Exception e) {
			log.debug("用户登录未处理异常: {}", e.getMessage());
			return ResponseEntity.ok(
					new BaseResultBuilder<LoginSuccessDTO>().syserr().build());
		}
	}
	
	
	/**
	 * 
	 * 用户登出
	 */
	@PostMapping("/logout")
	public void logout() {
		
	}
	
	
	/**
	 * 刷新token
	 * @param req  请求
	 * @param token  token
	 * @param rememberMeValue  
	 * @return
	 */
	@ApiOperation(value = "用户token刷新", notes="用户token刷新", 
			responseReference="com.iveiv.rbac.base.result.BaseResult<String>", httpMethod="POST")
	@PostMapping("/refresh")
	public ResponseEntity<BaseResult<String>> autoRefresh(
			@ApiIgnore HttpServletRequest req, 
			@RequestParam("token") final String token, 
			@RequestParam("rememberMeSecret") final String rememberMeSecret) {
		
		// 获取请求头
		final RequestHeaderDTO headers = RequestHeaderDTO.headers(req);
		
		if(headers == null) {
			// 发出401
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		
		try {
			
			BaseResult<String> autoTokenRefresh = tokenAuthService.autoTokenRefresh(headers, token, rememberMeSecret);
			
			// 返回结果
			return ResponseEntity.ok(autoTokenRefresh);
		} catch (Exception e) {
			log.debug("用户刷新token未处理异常: {}", e.getMessage());
			return ResponseEntity.ok(
					new BaseResultBuilder<String>().syserr().build());
		}
	}
	
	
	/**
	 * Token 验证
	 * @param req
	 * @param token
	 * @return
	 */
	@ApiOperation(value = "token验证", notes="token验证", 
			responseReference="com.iveiv.rbac.base.result.BaseResult<String>", httpMethod="POST")
	@PostMapping("/autoVerify")
	public ResponseEntity<BaseResult<String>> autoVerifyToken(
			@ApiIgnore HttpServletRequest req, 
			@RequestParam("token") final String token) {
		
		// 获取请求头
		final RequestHeaderDTO headers = RequestHeaderDTO.headers(req);
		
		if(headers == null) {
			// 发出401
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		
		try {
			
			BaseResult<String> autoVerifyToken = tokenAuthService.autoVerifyToken(headers, token);
			
			return ResponseEntity.ok(autoVerifyToken);
		} catch (Exception e) {
			log.debug("token验证未处理异常: {}", e.getMessage());
			return ResponseEntity.ok(
					new BaseResultBuilder<String>().syserr().build());
		}
		
		
	}
	
	
	/**
	 *   扫码接口
	 * @param req
	 * @param token
	 * @param qrToken
	 * @return
	 */
	@ApiOperation(value = "发起二维码扫码登录", notes="", 
			responseReference="com.iveiv.rbac.base.result.BaseResult<String>", httpMethod="POST")
	@PostMapping("/qrcodeLoginByScan")
	@ResponseBody
	public ResponseEntity<BaseResult<ScanQrcodeLoginServerDTO>> qrcodeLoginByScan(
			@ApiIgnore HttpServletRequest req, 
		    @RequestParam("token") String token, 
		    @RequestParam("qrcodeToken") String qrcodeToken) {
		
		// 获取请求头
		final RequestHeaderDTO headers = RequestHeaderDTO.headers(req);
		
		if(headers == null) {
			// 发出401
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		
		try {
			
			// 用户扫码
			final BaseResult<ScanQrcodeLoginServerDTO> result = tokenAuthService.qrcodeLoginByScan(headers, token, qrcodeToken);
			
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			log.debug("token验证未处理异常: {}", e.getMessage());
			return ResponseEntity.ok(
					new BaseResultBuilder<ScanQrcodeLoginServerDTO>().syserr().build());
		}
		
		
		
		
		
	}
	
	
	/**
	 * 扫码确认接口
	 * @param req
	 * @param token
	 * @param qrToken
	 * @return
	 */
	@ApiOperation(value = "用户扫码确认接口", notes="", 
			responseReference="com.iveiv.rbac.base.result.BaseResult<String>", httpMethod="POST")
	@PostMapping("/qrcodeConfirmLogin")
	@ResponseBody
	public ResponseEntity<BaseResult<String>> qrcodeConfirmLogin(
			@ApiIgnore HttpServletRequest req, 
			@RequestParam("token") String token, 
			@RequestParam("qrcodeToken") String qrcodeToken) {
		
		// 获取请求头
		final RequestHeaderDTO headers = RequestHeaderDTO.headers(req);
		
		if(headers == null) {
			// 发出401
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
		
		try {
			
			// 用户确认
			final BaseResult<String> result = tokenAuthService.qrcodeConfirmLogin(headers, token, qrcodeToken);
			
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			log.debug("token验证未处理异常: {}", e.getMessage());
			return ResponseEntity.ok(
					new BaseResultBuilder<String>().syserr().build());
		}
		
	}
	
	
	
	
	
	

}
