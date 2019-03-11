package com.iveiv.rbac.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 加密版本
 * @author irays
 *
 */
public enum AppVersionEnum {
	
		/**
		 * 默认
		 */
		DEFAULT_V("default"),
		/**
		 * 版本1
		 */
		V1("v1");
		
		
		private String code;
		
		AppVersionEnum(String code) {
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
		
		
		public static AppVersionEnum getEnum(String code) {
			AppVersionEnum resultEnum = null;
			AppVersionEnum[] enumAry = AppVersionEnum.values();
			
			for (int i = 0; i < enumAry.length; i++) {
				if (enumAry[i].getCode().equals(code)) {
					resultEnum = enumAry[i];
					break;
				}
			}
			return resultEnum;
		}

		public static Map<String, String> toMap() {
			AppVersionEnum[] ary = AppVersionEnum.values();
			Map<String, String> enumMap = new HashMap<String, String>();
			for (int num = 0; num < ary.length; num++) {
				
				String code = ary[num].getCode();
				String key = String.valueOf(ary[num]);
				enumMap.put(key,  code);
				
			}
			return enumMap;
		}

}
