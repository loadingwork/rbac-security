package com.lgwork.sys;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lgwork.api.exceptions.BizException;
import com.lgwork.base.constant.ErrorPageConst;
import com.lgwork.base.result.BaseResult;
import com.lgwork.base.result.BaseResultBuilder;
import com.lgwork.enums.ErrorCodeEnum;
import com.lgwork.util.JsonBodyUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 *  系统模块系统控制器
 * 
 * @author irays
 *
 */
@Slf4j
@ControllerAdvice(basePackages = {"com.lgwork.sys.controller"})
public class SysExceptionAdvice {

	
	/**
	 * 
	 * 不可预期的异常
	 * 
	 * @param req
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(Exception.class)
    public String handleException(HttpServletRequest req,Exception e){
		
		if (req.getRequestURI() != null 
				&& req.getRequestURI().contains(".do")) {
			// 跳转到404页面
			return ErrorPageConst.ERROR404;
		}
		
		// 返回json
		BaseResult<String> result = new BaseResultBuilder<String>().enums(ErrorCodeEnum.SYS_ERROR).build();
		String jsonResult = JsonBodyUtils.toJSONString(result);
		
        return jsonResult;
    }
	
	
	/**
	 * 业务异常处理
	 * @param e
	 * @return
	 */
	@ExceptionHandler(BizException.class)
	@ResponseBody
	public BaseResult<String> handleBizException(BizException e) {
		
		log.error("handleBizException >> {} >> {}", e.getCode(), e.getMsg());
		
		return new BaseResultBuilder<String>().enums(ErrorCodeEnum.SYS_ERROR).build();
	}
	
	
}
