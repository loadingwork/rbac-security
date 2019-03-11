package com.iveiv.rbac.api.exceptions;


/**
 * 
 * 业务异常
 * 
 * @author irays
 *
 */
public class BizException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8915803120264758103L;
	
	public static final BizException APP_REQUEST_KEY_NOT_NULL = new BizException(10000, "app请求加密key不能为空");
	public static final BizException LOCAL_RSA_KEY_NOT_NULL = new BizException(10001, "app加密私钥key不能为空");
	public static final BizException LOCAL_PUB_RSA_KEY_NOT_NULL = new BizException(10002, "app加密公钥key不能为空");
	public static final BizException LOCAL_RSA_KEY_DECRYPT_FAIL = new BizException(10003, "app加密私钥解密失败");
	
	
	public static final BizException USER_ACCOUNT_NOT_FINDED = new BizException(10004, "用户账号不存在");
	public static final BizException USER__NOT_FINDED = new BizException(10005, "用户账户不存在");
	public static final BizException MISSING_PARAMETER = new BizException(10006, "缺少参数");
	public static final BizException CREDENTIALS_VALIDATE_FAIL = new BizException(10007, "凭证验证失败");
	public static final BizException DIFFERENCE_DEFAULTD_APP_KEY = new BizException(10008, "不同的defaultdAppKey");
	public static final BizException ILLEGAL_REQUEST_HEADER = new BizException(10009, "异常请求头");
	public static final BizException MISSING_REQUEST_HEADER = new BizException(10010, "缺少请求头");
	
	
	public static final BizException TOKEN_ENCRYPT_FAIL = new BizException(20001, "token加密失败");
	public static final BizException TOKEN_DECRYPT_FAIL = new BizException(20002, "token解密异常");
	public static final BizException TOKEN_FORMAT_DECODE_FAIL = new BizException(20003, "token格式错误");
	public static final BizException TOKEN_DECRYPT_MODE_LESS = new BizException(20004, "token解密方式缺失");
	public static final BizException TOKEN_VERIFY_SIGNATURE_FAIL = new BizException(20005, "token验证签名失败");
	public static final BizException TOKEN_ALREADY_EXPIRE = new BizException(20006, "token过期");
	public static final BizException TOKEN_ALREADY_INVALID = new BizException(20007, "token失效");
	public static final BizException TOKEN_DECODE_PAYLOAD = new BizException(20008, "token的Payload解析错误");
	
	
	public static final BizException TOKEN_REMEMBER_ME_ENCRYPT_FAIL = new BizException(20009, "TokenRememberMe加密失败");
	public static final BizException TOKEN_REMEMBER_ME_DECRYPT_FAIL = new BizException(20010, "TokenRememberMe解密失败");
	
	public static final BizException TOKEN_REMEMBER_ME_EXPIRE = new BizException(20013, "TokenRememberMe已经过期");
	public static final BizException TOKEN_REMEMBER_ME_INVALID = new BizException(20014, "TokenRememberMe已经失效");
	
	public static final BizException DEVICE_CODE_NOT_FIND = new BizException(20015, "产品编号不存在");
	public static final BizException TOO_MANY_ATTEMPTS = new BizException(20016, "过多尝试");
	public static final BizException TIMEOUT_ATTEMPT = new BizException(20016, "超时尝试");

	/**
	 * 异常信息
	 */
	protected String msg;

	/**
	 * 具体异常码
	 */
	protected int code;

	public BizException(int code, String msgFormat, Object... args) {
		super(String.format(msgFormat, args));
		this.code = code;
		this.msg = String.format(msgFormat, args);
	}

	public BizException() {
		super();
	}

	public String getMsg() {
		return msg;
	}

	public int getCode() {
		return code;
	}

	/**
	 * 实例化异常
	 * 
	 * @param msgFormat
	 * @param args
	 * @return
	 */
	public BizException newInstance(String msgFormat, Object... args) {
		return new BizException(this.code, msgFormat, args);
	}

	public BizException(String message, Throwable cause) {
		super(message, cause);
	}

	public BizException(Throwable cause) {
		super(cause);
	}

	public BizException(String message) {
		super(message);
	}
	
	

}
