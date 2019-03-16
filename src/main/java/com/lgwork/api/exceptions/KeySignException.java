package com.lgwork.api.exceptions;

/**
 * 
 *  key加密数据异常
 * 
 * @author irays
 *
 */
public class KeySignException extends BizException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 262621787170036538L;
	
	
	
	public KeySignException() {
	}

	public KeySignException(String message) {
		super(-1, message);
		printStackTrace();
	}

	public KeySignException(Integer code, String message) {
		super(code, message);
		printStackTrace();
	}
	
	

}
