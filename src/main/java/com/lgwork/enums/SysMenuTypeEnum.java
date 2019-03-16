package com.lgwork.enums;


/**
 * 菜单类型枚举
 * @author irays
 *
 */
public enum SysMenuTypeEnum {
	
	/**
	 * 菜单
	 */
	MENU("menu"),
	/**
	 * 按钮
	 */
	BTN("btn"),
	/**
	 * 未知
	 */
	UNKNOWN("unknown");
	
	private String code;
	
	SysMenuTypeEnum(String code) {
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

}
