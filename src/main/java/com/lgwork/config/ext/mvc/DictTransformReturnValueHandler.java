package com.lgwork.config.ext.mvc;

import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.lgwork.base.annotation.DictTransform;


/**
 * 自定义参数处理
 * @author irays
 *
 */
public class DictTransformReturnValueHandler implements HandlerMethodReturnValueHandler {

	@Override
	public boolean supportsReturnType(MethodParameter returnType) {
		
		System.err.println(returnType);
		
		return returnType.hasParameterAnnotation(DictTransform.class);
	}

	@Override
	public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest) throws Exception {
		
		System.err.println(returnValue);
		System.err.println(returnType);
		System.err.println(mavContainer);
		System.err.println(webRequest);
		
	}

}
