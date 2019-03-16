package com.iveiv.rbac.base.ext.jackson;

import java.io.IOException;
import java.util.Objects;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.iveiv.rbac.base.annotation.SensitiveInfo;
import com.iveiv.rbac.enums.DesensitizationEnum;

/**
 * 自定义脱敏序列化
 * @author irays
 *
 */
//@Slf4j
public class DesensitizationSerialize extends JsonSerializer<String> implements ContextualSerializer {
	
	/**
	 * 脱敏类型
	 */
	private DesensitizationEnum type;
	
	public DesensitizationSerialize() {
		super();
	}

	public DesensitizationSerialize(DesensitizationEnum type) {
		super();
		this.type = type;
	}

	public DesensitizationEnum getType() {
		return type;
	}

	@Override
	public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property)
			throws JsonMappingException {
	
		if (property != null) {
			// 跳过空值
			if (Objects.equals(property.getType().getRawClass(), String.class)) {
				// 必须是string类型
				SensitiveInfo sensitiveInfo = property.getAnnotation(SensitiveInfo.class);
				
				if (sensitiveInfo != null) {
					
					return new DesensitizationSerialize(sensitiveInfo.value());
				}
				
			}
			
			return prov.findValueSerializer(property.getType(), property);
		}
		
		// 获取空值序列化器
		return prov.findNullValueSerializer(property);
	}

	@Override
	public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		
		switch (this.type) {
			case ID_CARD:
			default:
				gen.writeString("******");
				break; 
		}
		
		gen.writeString("");
	}

}
