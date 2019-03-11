package com.iveiv.rbac.base.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.iveiv.rbac.enums.SysLogEnum;


/**
 * 日记切点
 * @author irays
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface SysLogPoint {
	
	
	/**
	 * 日记类型
	 * @return
	 */
	public SysLogEnum type() default SysLogEnum.OTHER;
	
	

}
