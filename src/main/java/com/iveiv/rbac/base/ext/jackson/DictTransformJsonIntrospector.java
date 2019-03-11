package com.iveiv.rbac.base.ext.jackson;

import java.lang.annotation.Annotation;

import org.springframework.boot.jackson.JsonComponent;

import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.iveiv.rbac.base.annotation.DictTransform;

@JsonComponent
public class DictTransformJsonIntrospector extends JacksonAnnotationIntrospector  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	

	@Override
	public boolean isAnnotationBundle(Annotation ann) {
		
		Class<? extends Annotation> annotationType = ann.annotationType();
		
		if (annotationType == DictTransform.class) {
			return true;
		}
		
		return super.isAnnotationBundle(ann);
	}
	
	
	
	

	
	
}
