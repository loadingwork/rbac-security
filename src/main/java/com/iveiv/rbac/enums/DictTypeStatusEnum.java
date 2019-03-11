package com.iveiv.rbac.enums;

import java.util.HashMap;
import java.util.Map;


/**
 * 数据字典状态
 * @author irays
 *
 */
public enum DictTypeStatusEnum {
	

	
	/**
	 * 正常
	 */
	OK("ok"),
	/**
	 * 停用
	 */
	DISABLE("disable");
	
	
	private String code;
	
	DictTypeStatusEnum(String code) {
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
	
	
	public static DictTypeStatusEnum getEnum(String code) {
		DictTypeStatusEnum resultEnum = null;
		DictTypeStatusEnum[] enumAry = DictTypeStatusEnum.values();
		
		for (int i = 0; i < enumAry.length; i++) {
			if (enumAry[i].getCode().equals(code)) {
				resultEnum = enumAry[i];
				break;
			}
		}
		return resultEnum;
	}

	public static Map<String, String> toMap() {
		DictTypeStatusEnum[] ary = DictTypeStatusEnum.values();
		Map<String, String> enumMap = new HashMap<String, String>();
		for (int num = 0; num < ary.length; num++) {
			
			String code = ary[num].getCode();
			String key = String.valueOf(ary[num]);
			enumMap.put(key,  code);
			
		}
		return enumMap;
	}
	
	


}
