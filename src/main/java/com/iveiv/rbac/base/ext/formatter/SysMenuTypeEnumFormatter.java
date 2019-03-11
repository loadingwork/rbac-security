package com.iveiv.rbac.base.ext.formatter;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.format.Formatter;

import com.iveiv.rbac.enums.SysMenuTypeEnum;


/**
 * 
 * mvc数据转换
 * SysMenuTypeEnum
 * 
 * @author irays
 *
 */
public class SysMenuTypeEnumFormatter implements Formatter<SysMenuTypeEnum> {

	
	/**
	 * 转字符串
	 */
	@Override
	public String print(SysMenuTypeEnum object, Locale locale) {
		if (object == null) {
			return SysMenuTypeEnum.UNKNOWN.getCode();
		}
		return object.getCode();
	}

	/**
	 * 字符串转对象
	 */
	@Override
	public SysMenuTypeEnum parse(String text, Locale locale) throws ParseException {
		
		if (SysMenuTypeEnum.MENU.value().equals(text)) {
			
			return SysMenuTypeEnum.MENU;
		} else if (SysMenuTypeEnum.BTN.value().equals(text)) {
			
			return SysMenuTypeEnum.BTN;
		}
		
		return SysMenuTypeEnum.UNKNOWN;
	}

}
