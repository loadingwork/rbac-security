package com.iveiv.rbac.util;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * 
 * 请求实体json辅助工具类
 * 
 * @author irays
 *
 */
public class JsonBodyUtils {
	
	/**
	 * 
	    QuoteFieldNames———-输出key时是否使用双引号,默认为true 
		WriteMapNullValue——–是否输出值为null的字段,默认为false 
		WriteNullNumberAsZero—-数值字段如果为null,输出为0,而非null 
		WriteNullListAsEmpty—–List字段如果为null,输出为[],而非null 
		WriteNullStringAsEmpty—字符类型字段如果为null,输出为”“,而非null 
		WriteNullBooleanAsFalse–Boolean字段如果为null,输出为false,而非null
	 * 
	 */
	
	private static final SerializerFeature[] FEATURES = new SerializerFeature[] {
			SerializerFeature.WriteEnumUsingToString,
            SerializerFeature.WriteNullStringAsEmpty,
            SerializerFeature.WriteMapNullValue,
            SerializerFeature.WriteDateUseDateFormat,
            SerializerFeature.WriteNullListAsEmpty
	};
	
	
	/**
	 * 
	 * 对象转json
	 * 
	 * @param object
	 * @return
	 */
	public static String toJSONString(Object object) {
		
//		JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd";
		
		return JSONObject.toJSONString(object, FEATURES);
	}
	
	
	/**
	 * json转object
	 * @param text
	 * @param clazz
	 * @return
	 */
	public static <T> T parseObject(String text, Class<T> clazz) {
		return JSONObject.parseObject(text, clazz);
	}
	
	/**
	 * @param text
	 * @param clazz
	 * @return
	 */
	public static <T> List<T> parseArray(String text, Class<T> clazz) {
		return JSONArray.parseArray(text, clazz);
	}
	
	
	

}
