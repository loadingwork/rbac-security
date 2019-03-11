package com.iveiv.rbac.api;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.iveiv.rbac.base.result.BaseResult;
import com.iveiv.rbac.base.result.BaseResultBuilder;
import com.iveiv.rbac.enums.ErrorCodeEnum;


/**
 * 
 * api接口异常统一处理
 * 
 * extends AbstractJsonpResponseBodyAdvice
 * 
 * @author irays
 *
 */
@ControllerAdvice(basePackages= {"com.iveiv.rbac.api.controller"})
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
