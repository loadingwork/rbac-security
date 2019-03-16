package com.lgwork.api;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lgwork.base.result.BaseResult;
import com.lgwork.base.result.BaseResultBuilder;
import com.lgwork.enums.ErrorCodeEnum;


/**
 * 
 * api接口异常统一处理
 * 
 * extends AbstractJsonpResponseBodyAdvice
 * 
 * @author irays
 *
 */
@ControllerAdvice(basePackages= {"com.lgwork.api.controller"})
public class TokenExceptionAdvice {
	
	
	@ExceptionHandler(Exception.class)
    @ResponseBody
    BaseResult<String> handleException(HttpServletRequest req,Exception exception){
		exception.printStackTrace();
		System.out.println(req);
		System.out.println(req.getRequestURL());
		System.out.println(req.getRequestURI());
        return new BaseResultBuilder<String>().enums(ErrorCodeEnum.SYS_ERROR).build();
    }
	

}
