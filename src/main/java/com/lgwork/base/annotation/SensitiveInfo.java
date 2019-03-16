package com.lgwork.base.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.lgwork.base.ext.jackson.DesensitizationSerialize;
import com.lgwork.enums.DesensitizationEnum;

/**
 * 脱敏注解
 * 
 * @author irays
 *
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@JacksonAnnotationsInside
@JsonSerialize(using = DesensitizationSerialize.class)
public @interface SensitiveInfo {

	/**
	 * 数据脱敏类型
	 * @return
	 */
	public DesensitizationEnum value();

}
