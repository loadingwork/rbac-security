package com.lgwork.enums;

/**
 * 
 * 用户结果自定义枚举
 * 
 * 错误反馈模式:
 * 1 client
 * 2 server (默认)
 * 
 * 
 * @author irays
 *
 */
public enum ErrorCodeEnum {

	/* 系统错误 */
	SYS_ERROR("-1", "system busy"),
	/* 成功 */
	SUCCESS("0", "ok"),
	FAIL("1", "fail"),
	MISSING_KEY_PARAMETERS("1", "关键参数缺失"),
	NO_FOUND_KEY_DATA("1", "关键数据没有找到"),
	TOKEN_EXPIRES("2", "Token过期"),
	TOKEN_INVALID("3", "Token失效"),
	TOKEN_TAMPERED("4", "Token被篡改"),
	TOKEN_REMEMBERME_EXPIRES("5", "RememberMe过期"),
	TOKEN_REMEMBERME_INVALID("6", "RememberMe失效"),
	TOKEN_REMEMBERME_TAMPERED("7", "RememberMe信息被篡改"),
	QRCODE_TOKEN_TINVALID("8", "qrcodeToken失效"),
	NO_BIND_ACCOUNT("9", "没有绑定后台账号"),
	ILLEGAL_DOCUMENT_DETECTION("10", "异常文件");
	
	/**
	 * 错误码
	 */
	private String errcode;
	/**
	 * 错误默认信息
	 */
	private String errmsg;

	private ErrorCodeEnum(String errcode, String errmsg) {
		this.errcode = errcode;
		this.errmsg = errmsg;
	}

	public String getErrcode() {
		return errcode;
	}

	public void setErrcode(String errcode) {
		this.errcode = errcode;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

}
