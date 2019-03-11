package com.iveiv.rbac.base.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义权限控制器
 * @author irays
 *
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysPermission {
	
	/**
	 * 权限字符串
	 * @return
	 */
	String value();
	
	/**
	 * 权限类型
	 * 
	 * api
	 * action
	 * 
	 * @return
	 */
	String type() default "action";

}
