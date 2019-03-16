package com.lgwork.base.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.lgwork.base.ext.jackson.DictTransformSerializer;

/**
 * 自定义枚举转换接口
 * @author irays
 *
 */
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@JacksonAnnotationsInside	
@JsonSerialize(using = DictTransformSerializer.class)
public @interface DictTransform {
	
	/**
	 * 数据字典编码
	 * @return
	 */
	String type();
	/**
	 * 数据字典项编码
	 * @return
	 */
	String code();
	/**
	 * 数据字典项名称
	 * @return
	 */
	String text() default "";
	
	
	

}
