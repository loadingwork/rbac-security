package com.iveiv.rbac.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 产品编号
 * @author irays
 *
 */
public enum DeviceCodeEnum {
	

	/**
	 * 系统后台
	 */
	SADMIN("1000");
	
	private String code;
	
	DeviceCodeEnum(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public String value() {
		return code;
	}
	
	
	public static DeviceCodeEnum getEnum(String code) {
		DeviceCodeEnum resultEnum = null;
		DeviceCodeEnum[] enumAry = DeviceCodeEnum.values();
		
		for (int i = 0; i < enumAry.length; i++) {
			if (enumAry[i].getCode().equals(code)) {
				resultEnum = enumAry[i];
				break;
			}
		}
		return resultEnum;
	}

	public static Map<String, String> toMap() {
		DeviceCodeEnum[] ary = DeviceCodeEnum.values();
		Map<String, String> enumMap = new HashMap<String, String>();
		for (int num = 0; num < ary.length; num++) {
			
			String code = ary[num].getCode();
			String key = String.valueOf(ary[num]);
			enumMap.put(key,  code);
			
		}
		return enumMap;
	}
	
	


}
