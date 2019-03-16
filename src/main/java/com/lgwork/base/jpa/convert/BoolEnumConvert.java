package com.lgwork.base.jpa.convert;

import javax.persistence.AttributeConverter;

import com.lgwork.enums.BoolEnum;

/**
 * 
 * bool自定义枚举转换
 * 
 * @Convert( converter = BoolEnumConvert.class )
 * 
 * @author irays
 *
 */
public class BoolEnumConvert implements AttributeConverter<BoolEnum, Integer> {

	@Override
	public Integer convertToDatabaseColumn(BoolEnum attribute) {
		if (attribute != null) {
			return attribute.getValue();
		}
		return 0;
	}

	@Override
	public BoolEnum convertToEntityAttribute(Integer dbData) {
		
		if (dbData != null) {
			BoolEnum boolEnum = BoolEnum.getEnum(dbData);
			if (boolEnum != null) {
				return boolEnum;
			}
		}
		
		// 默认返回false
		return BoolEnum.FALSE;
	}


}
