package com.lgwork.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 设备枚举
 * @author irays
 *
 */
public enum DeviceClientEnum {
	
	// 安卓
	ANDROID("android"),
	// 苹果
	IPHONE("iphone"),
	// 微软wp
	WP("wp"),
	// h5浏览器
	H5("h5"),
	// 微信浏览器
	WX("wx"),
	// 小程序
	MINIAPP("miniapp"),
	// 未知
	UNKNOWN("unknown");
	
	private String code;
	
	DeviceClientEnum(String code) {
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
	
	
	public static DeviceClientEnum getEnum(String code) {
		DeviceClientEnum resultEnum = null;
		DeviceClientEnum[] enumAry = DeviceClientEnum.values();
		
		for (int i = 0; i < enumAry.length; i++) {
			if (enumAry[i].getCode().equals(code)) {
				resultEnum = enumAry[i];
				break;
			}
		}
		return resultEnum;
	}

	public static Map<String, String> toMap() {
		DeviceClientEnum[] ary = DeviceClientEnum.values();
		Map<String, String> enumMap = new HashMap<String, String>();
		for (int num = 0; num < ary.length; num++) {
			
			String code = ary[num].getCode();
			String key = String.valueOf(ary[num]);
			enumMap.put(key,  code);
			
		}
		return enumMap;
	}
	
	
}
