package com.lgwork.api.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lgwork.api.domain.dto.RequestHeaderDTO;
import com.lgwork.api.service.TokenRememberMeAuthService;
import com.lgwork.base.result.BaseResult;
import com.lgwork.base.result.BaseResultBuilder;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 
 * 用户记住登录认证控制器
 * 
 * @author irays
 *
 */
@Api(tags= {"记住密码信息认证"})
@Slf4j
@RequestMapping("/api/token/rememberme/")
@RestController
public class TokenRememberMeAuthController {

	/**
	 * 记住登录服务接口
	 */
	@Autowired
	private TokenRememberMeAuthService tokenRememberMeAuthService;

	/**
	 * 自动登录
	 * 
	 * @param req
	 * @param resp
	 * @param requestBody
	 * @return
	 */
	@ApiOperation(value = "记住密码登录", notes="", 
			responseReference="com.lgwork.base.result.BaseResult<String>", httpMethod="POST")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "rememberMeValue", value = "记住密码信息", required = true, dataType = "String"),
	})
	@PostMapping("/autoLogin")
	public ResponseEntity<BaseResult<String>> autoTokenRememberMeLogin(
		    @ApiIgnore HttpServletRequest req,
			@RequestParam("rememberMeSecret") final String rememberMeSecret) {

		// 获取请求头
		final RequestHeaderDTO headers = RequestHeaderDTO.headers(req);

		if (headers == null) {
			// 发出401
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		try {

			BaseResult<String> autoTokenRememberMeLogin = tokenRememberMeAuthService.autoTokenRememberMeLogin(headers,
					rememberMeSecret);

			return ResponseEntity.ok(autoTokenRememberMeLogin);
		} catch (Exception e) {
			log.debug("用户刷新token未处理异常: {}", e.getMessage());
			return ResponseEntity.ok(new BaseResultBuilder<String>().syserr().build());
		}
	}

	/**
	 * RememberMe刷新
	 * 
	 * @param req
	 * @param resp
	 * @param requestBody
	 * @return
	 */
	@ApiOperation(value = "记住密码信息刷新", notes="", 
			responseReference="com.lgwork.base.result.BaseResult<String>", httpMethod="POST")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "rememberMeValue", value = "记住密码信息", required = true, dataType = "String"),
	})
	@PostMapping("/refresh")
	public ResponseEntity<BaseResult<String>> refreshRememberMeInfo(
			@ApiIgnore HttpServletRequest req,
			@RequestParam("rememberMeSecret") final String rememberMeSecret) {

		// 获取请求头
		final RequestHeaderDTO headers = RequestHeaderDTO.headers(req);

		if (headers == null) {
			// 发出401
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}

		try {

			BaseResult<String> autoTokenRememberMeLogin = tokenRememberMeAuthService.autoTokenRememberMeRefresh(headers,
					rememberMeSecret);

			return ResponseEntity.ok(autoTokenRememberMeLogin);

		} catch (Exception e) {
			log.debug("用户刷新token未处理异常: {}", e.getMessage());
			return ResponseEntity.ok(new BaseResultBuilder<String>().syserr().build());
		}

	}

}
