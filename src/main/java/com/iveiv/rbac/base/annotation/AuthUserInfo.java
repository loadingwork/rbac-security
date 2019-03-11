package com.iveiv.rbac.base.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

/**
 * 自定义获取用户信息注解
 * com.iveiv.rbac.config.ext.UserInfoDetails
 * @author irays
 *
 */
@Target({ ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@AuthenticationPrincipal
public @interface AuthUserInfo  {
	
	/**
	 * 一个默认就是value
	 * @return
	 */
	String value() default "";

}
