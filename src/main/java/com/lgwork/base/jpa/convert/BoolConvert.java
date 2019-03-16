package com.lgwork.base.jpa.convert;

import javax.persistence.AttributeConverter;


/**
 * 自定义Boolean转换
 * @author irays
 *
 */
public class BoolConvert  implements AttributeConverter<Boolean, Integer> {

	@Override
	public Integer convertToDatabaseColumn(Boolean attribute) {
		if (attribute == null) {
			return 0;
		}
		
		if (attribute) {
			return 1;
		}
		
		return 0;
	}

	@Override
	public Boolean convertToEntityAttribute(Integer dbData) {
		if (dbData == null) {
			return false;
		}
		
		if (dbData == 1) {
			return true;
		}
		
		return false;
	}

}
