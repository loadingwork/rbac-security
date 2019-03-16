package com.lgwork.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 扫码协议
 * 
 * @author irays
 *
 */
public enum QrcodeProtocolEnum {

	// 二维码登录
	QR_LOGIN("qrlogin://");

	private String code;

	QrcodeProtocolEnum(String code) {
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

	public static QrcodeProtocolEnum getEnum(String code) {
		QrcodeProtocolEnum resultEnum = null;
		QrcodeProtocolEnum[] enumAry = QrcodeProtocolEnum.values();

		for (int i = 0; i < enumAry.length; i++) {
			if (enumAry[i].getCode().equals(code)) {
				resultEnum = enumAry[i];
				break;
			}
		}
		return resultEnum;
	}

	public static Map<String, String> toMap() {
		QrcodeProtocolEnum[] ary = QrcodeProtocolEnum.values();
		Map<String, String> enumMap = new HashMap<String, String>();
		for (int num = 0; num < ary.length; num++) {

			String code = ary[num].getCode();
			String key = String.valueOf(ary[num]);
			enumMap.put(key, code);

		}
		return enumMap;
	}

}
