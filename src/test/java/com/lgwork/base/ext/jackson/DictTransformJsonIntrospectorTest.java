package com.lgwork.base.ext.jackson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DictTransformJsonIntrospectorTest {
	
	private final static ObjectMapper objectMapper = new ObjectMapper();
	 
    static {
//        AnnotationIntrospector dimensionFieldSerializer = new DictTransformJsonIntrospector();
//        objectMapper.setAnnotationIntrospector(dimensionFieldSerializer);
    }

	public static void main(String[] args) throws JsonProcessingException {
		
		JscksonTest jscksonTest = new JscksonTest();
		jscksonTest.setName("111");
		
		String jsonString = objectMapper.writeValueAsString(jscksonTest);
		
		System.out.println(jsonString);
		
	}

}
