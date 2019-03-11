package com.iveiv.rbac.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * 第三方登录枚举
 * 
 * @author irays
 *
 */
public enum ThirdPartyOauthPlateformEnum {
	
	
	/**
	 * 微信小程序
	 */
	WX_MINI_APP("wxmapp");
	
	private String code;
	
	ThirdPartyOauthPlateformEnum(String code) {
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
	
	
	public static ThirdPartyOauthPlateformEnum getEnum(String code) {
		ThirdPartyOauthPlateformEnum resultEnum = null;
		ThirdPartyOauthPlateformEnum[] enumAry = ThirdPartyOauthPlateformEnum.values();
		
		for (int i = 0; i < enumAry.length; i++) {
			if (enumAry[i].getCode().equals(code)) {
				resultEnum = enumAry[i];
				break;
			}
		}
		return resultEnum;
	}

	public static Map<String, String> toMap() {
		ThirdPartyOauthPlateformEnum[] ary = ThirdPartyOauthPlateformEnum.values();
		Map<String, String> enumMap = new HashMap<String, String>();
		for (int num = 0; num < ary.length; num++) {
			
			String code = ary[num].getCode();
			String key = String.valueOf(ary[num]);
			enumMap.put(key,  code);
			
		}
		return enumMap;
	}
	
	
	

}
