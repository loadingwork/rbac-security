package com.lgwork.base.jpa.convert;

import javax.persistence.AttributeConverter;

import com.lgwork.enums.SysMenuTypeEnum;


/**
 * jpa 数据类型转换
 * @author irays
 *
 */
public class SysMenuTypeEnumConvert implements AttributeConverter<SysMenuTypeEnum, String> {

	@Override
	public String convertToDatabaseColumn(SysMenuTypeEnum attribute) {
		
		if (attribute == null) {
			return SysMenuTypeEnum.UNKNOWN.value();
		}
		
		return attribute.value();
	}

	@Override
	public SysMenuTypeEnum convertToEntityAttribute(String dbData) {
		
		if (SysMenuTypeEnum.MENU.value().equals(dbData)) {
			
			return SysMenuTypeEnum.MENU;
		} else if (SysMenuTypeEnum.BTN.value().equals(dbData)) {
			
			return SysMenuTypeEnum.BTN;
		}
		
		return SysMenuTypeEnum.UNKNOWN;
		
	}

}
