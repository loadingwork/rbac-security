package com.lgwork.base;

import org.junit.Test;

import com.google.common.base.Converter;
import com.google.common.base.Enums;
import com.lgwork.enums.BoolEnum;

public class EnumsTest {
	
	
	/**
	 * 获取枚举
	 */
	@Test
	public void testEnums() {
//		Field field = Enums.getField(BoolEnum.FALSE);
		Converter<String, BoolEnum> stringConverter = Enums.stringConverter(BoolEnum.class);
		
		BoolEnum convert = stringConverter.convert("TRUE");
		
		System.err.println(convert);
		
//		ImmutableList.
		
		
		
	}

}
