package com.iveiv.rbac.base.ext.jackson;

import java.io.IOException;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.iveiv.rbac.base.annotation.DictTransform;
import com.iveiv.rbac.base.ext.SpringContextHolder;
import com.iveiv.rbac.sys.service.DictService;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class DictTransformSerializer extends JsonSerializer<String> implements ContextualSerializer {
	
	/**
	 * 数据字典类型编码
	 */
	private String type;
	/**
	 * 数据字典项编码
	 */
	private String code;
	/**
	 * 数据字典项名称
	 */
	private  String text;
	
	public DictTransformSerializer() {
		super();
	}

	public DictTransformSerializer(String type, String code, String text) {
		super();
		this.type = type;
		this.code = code;
		this.text = text;
	}

	public String getType() {
		return type;
	}



	public String getCode() {
		return code;
	}



	public String getText() {
		return text;
	}



	@Override
	public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property)
			throws JsonMappingException {
		
		
		if (property != null) {
			// 跳过空值
			if (Objects.equals(property.getType().getRawClass(), String.class)) {
				// 必须是string类型
				DictTransform dictTransform = property.getAnnotation(DictTransform.class);
				
				System.err.println(dictTransform);
				
				if (dictTransform != null) {
					
					return new DictTransformSerializer(dictTransform.type(), dictTransform.code(), dictTransform.text());
				}
				
			}
			
			return prov.findValueSerializer(property.getType(), property);
		}
		
		// 获取空值序列化器
		return prov.findNullValueSerializer(property);
	}

	@Override
	public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		
		if (StringUtils.isAnyEmpty(this.type, this.code)) {
			gen.writeString(value);
			return;
		}
		
		// 从spring上下文中获取字典转换对象
		DictService dictService = 
				SpringContextHolder.getBean("dictServiceImpl", DictService.class);
		
		if (dictService != null) {
			String dictConvertToText = dictService.dictConvertToText(this.type, this.code);
			log.debug("数据字典类型: {}, 数据字典项编码: {}, 转换成名称: {}", type, code, dictConvertToText);
			gen.writeString(dictConvertToText);
			return;
		}
		
		
		gen.writeString("");
	}

	
	
	

	

	
	

}
