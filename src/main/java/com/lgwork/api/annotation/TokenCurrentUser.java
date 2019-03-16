package com.lgwork.api.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 
 * 从token获取信息
 * 
 * @author irays
 *
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TokenCurrentUser {

	/**
	 * 参数默认只有一个value
	 * @return
	 */
	String value() default "";
	
}
