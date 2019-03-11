package com.iveiv.rbac.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * token状态
 * @author irays
 *
 */
public enum QrcodeTokenStatusEnum {

	/*
	 * 开始
	 */
	START("start"),
	/**
	 * 已经被使用
	 */
	ALREADY_USE("used");
	

	private String code;

	QrcodeTokenStatusEnum(String code) {
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

	public static QrcodeTokenStatusEnum getEnum(String code) {
		QrcodeTokenStatusEnum resultEnum = null;
		QrcodeTokenStatusEnum[] enumAry = QrcodeTokenStatusEnum.values();

		for (int i = 0; i < enumAry.length; i++) {
			if (enumAry[i].getCode().equals(code)) {
				resultEnum = enumAry[i];
				break;
			}
		}
		return resultEnum;
	}

	public static Map<String, String> toMap() {
		QrcodeTokenStatusEnum[] ary = QrcodeTokenStatusEnum.values();
		Map<String, String> enumMap = new HashMap<String, String>();
		for (int num = 0; num < ary.length; num++) {

			String code = ary[num].getCode();
			String key = String.valueOf(ary[num]);
			enumMap.put(key, code);

		}
		return enumMap;
	}

}
