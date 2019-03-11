package com.iveiv.rbac.base.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.iveiv.rbac.enums.LimitTypeEnum;

/**
 * 限流
 * 
 * 借鉴于 https://www.cnblogs.com/carrychan/p/9435979.html
 * 
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface FlowLimit {

	/**
	 * 资源的名字
	 *
	 * @return String
	 */
	String name() default "";

	/**
	 * 资源的key
	 *
	 * @return String
	 */
	String key() default "";

	/**
	 * Key的prefix
	 *
	 * @return String
	 */
	String prefix() default "";

	/**
	 * 给定的时间段 单位秒
	 *
	 * @return int
	 */
	int period();

	/**
	 * 最多的访问限制次数
	 *
	 * @return int
	 */
	int count();

	/**
	 * 类型
	 *
	 * @return LimitType
	 */
	LimitTypeEnum limitType() default LimitTypeEnum.CUSTOMER;	

}
