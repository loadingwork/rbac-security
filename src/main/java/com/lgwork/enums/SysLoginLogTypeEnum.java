package com.lgwork.enums;

import java.util.HashMap;
import java.util.Map;


/**
 * 用户登录成功类型
 * @author irays
 *
 */
public enum SysLoginLogTypeEnum {
	
	/**
	 * 记住密码登录
	 */
	REMEMBER_ME("remember_me"),
	/**
	 * 用户名认证
	 */
	USERNAME_AUTH("username_auth"),
	/**
	 * 基本认证
	 */
	BASIC_AUTH("basic_auth"),
	/**
	 * 二维码认证
	 */
	QRCODE_AUTH("qrcode_auth"),
	/**
	 * 其他认证
	 */
	OTHER_AUTH("other_auth");

	private String code;

	SysLoginLogTypeEnum(String code) {
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

	public static SysLoginLogTypeEnum getEnum(String code) {
		SysLoginLogTypeEnum resultEnum = null;
		SysLoginLogTypeEnum[] enumAry = SysLoginLogTypeEnum.values();

		for (int i = 0; i < enumAry.length; i++) {
			if (enumAry[i].getCode().equals(code)) {
				resultEnum = enumAry[i];
				break;
			}
		}
		return resultEnum;
	}

	public static Map<String, String> toMap() {
		SysLoginLogTypeEnum[] ary = SysLoginLogTypeEnum.values();
		Map<String, String> enumMap = new HashMap<String, String>();
		for (int num = 0; num < ary.length; num++) {

			String code = ary[num].getCode();
			String key = String.valueOf(ary[num]);
			enumMap.put(key, code);

		}
		return enumMap;
	}



}
