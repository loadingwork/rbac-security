package com.lgwork.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * token操作枚举
 * 
 * @author irays
 *
 */
public enum TokenOptionEnum {
	
	/**
	 * token通过登录获取
	 */
	TOKEN_BY_LOGIN("login"),
	/**
	 * token通过刷新获取
	 */
	TOKEN_BY_REFRESH("refresh"),
	/**
	 * 通过登录获取密钥值
	 */
	REMEMBER_ME_BY_LOGIN("me_login"),
	/**
	 * 记住密码刷新
	 */
	REMEMBER_ME_BY_REFRESH("me_refresh");
	
	private String code;
	
	
	TokenOptionEnum(String code) {
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
	
	
	public static TokenOptionEnum getEnum(String code) {
		TokenOptionEnum resultEnum = null;
		TokenOptionEnum[] enumAry = TokenOptionEnum.values();
		
		for (int i = 0; i < enumAry.length; i++) {
			if (enumAry[i].getCode().equals(code)) {
				resultEnum = enumAry[i];
				break;
			}
		}
		return resultEnum;
	}

	public static Map<String, String> toMap() {
		TokenOptionEnum[] ary = TokenOptionEnum.values();
		Map<String, String> enumMap = new HashMap<String, String>();
		for (int num = 0; num < ary.length; num++) {
			
			String code = ary[num].getCode();
			String key = String.valueOf(ary[num]);
			enumMap.put(key,  code);
			
		}
		return enumMap;
	}
	
	

}
