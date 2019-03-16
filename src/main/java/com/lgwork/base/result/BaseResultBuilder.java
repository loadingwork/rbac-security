package com.lgwork.base.result;

import com.lgwork.enums.ErrorCodeEnum;

/**
 * 
 * 基本结果构建
 * 
 * @author irays
 *
 * @param <T>
 */
public  class BaseResultBuilder<T> {
	
	/**
	 * 默认信息
	 */
	private String errcode;
	private String errmsg;
	
	private T data;
	
	private ErrorCodeEnum enums;
	
	public BaseResultBuilder<T> errcode() {
		this.errcode = ErrorCodeEnum.SUCCESS.getErrcode();
		return this;
	}
	
	public BaseResultBuilder<T> errcode(String errcode) {
		this.errcode = errcode;
		return this;
	}
	/**
	 * 自定错误
	 * @param errmsg
	 * @return
	 */
	public BaseResultBuilder<T> fail(String errmsg) {
		// 请求失败设置值
		this.errcode = ErrorCodeEnum.FAIL.getErrcode();
		this.errmsg = errmsg;
		return this;
	}
	
	public BaseResultBuilder<T> errmsg(String errmsg) {
		this.errmsg = errmsg;
		return this;
	}
	
	public BaseResultBuilder<T> data(T t) {
		this.data = t;
		return this;
	}
	
	public BaseResultBuilder<T> enums(ErrorCodeEnum enums) {
		this.enums = enums;
		return this;
	}
	
	/**
	 * 系统错误
	 * @return
	 */
	public BaseResultBuilder<T> syserr() {
		this.enums = ErrorCodeEnum.SYS_ERROR;
		return this;
	}
	
	/**
	 * token失效
	 * @return
	 */
	public BaseResultBuilder<T> invalidToken() {
		this.enums = ErrorCodeEnum.TOKEN_INVALID;
		return this;
	}
	/**
	 * 过期
	 * @return
	 */
	public BaseResultBuilder<T> expiresToken() {
		this.enums = ErrorCodeEnum.TOKEN_EXPIRES;
		return this;
	}
	
	/**
	 * 被篡改的token
	 * @return
	 */
	public BaseResultBuilder<T> tamperedToken() {
		this.enums = ErrorCodeEnum.TOKEN_TAMPERED;
		return this;
	}
	
	/**
	 * 记住密码信息过期
	 * @return
	 */
	public BaseResultBuilder<T> expiresRememberMe() {
		this.enums = ErrorCodeEnum.TOKEN_REMEMBERME_EXPIRES;
		return this;
	}
	/**
	 * RememberMe异常
	 * @return
	 */
	public BaseResultBuilder<T> invalidRememberMe() {
		this.enums = ErrorCodeEnum.TOKEN_REMEMBERME_INVALID;
		return this;
	}
	/**
	 * 被篡改记住登录信息
	 * @return
	 */
	public BaseResultBuilder<T> tamperedRememberMe() {
		this.enums = ErrorCodeEnum.TOKEN_REMEMBERME_TAMPERED;
		return this;
	}
	
	
	
	
	
	/**
	 * 构造最后结果
	 * @return
	 */
	public BaseResult<T> build() {
		BaseResult<T> result = new BaseResult<T>();
		
		// 设置数据
		result.setData(this.data);
		
		if(enums != null) {
			this.errcode = enums.getErrcode();
			if(this.errmsg == null) {
				this.errmsg = enums.getErrmsg();
			}
			
			result.setErrcode(this.errcode);
			result.setErrmsg(this.errmsg);
		} else {
			// 没有值
			if(this.errcode == null) {
				// 设置默认值
				this.errcode = ErrorCodeEnum.SUCCESS.getErrcode();
				this.errmsg = ErrorCodeEnum.SUCCESS.getErrmsg();
			}
			
			result.setErrcode(this.errcode);
			result.setErrmsg(this.errmsg);
		}
		
		return result;
	}
	
	/**
	 * 成功没有data
	 * @return
	 */
	public static BaseResult<String>  respSuccess() {
		return respSuccess(null);
	}
	
	/**
	 * 返回成功有data
	 * @param data
	 * @return
	 */
	public static BaseResult<String>  respSuccess(String data) {
		return new BaseResultBuilder<String>().data(data).build();
	}
	
	/**
	 * 返回构建对象
	 * @return
	 */
	public static BaseResultBuilder<String>  respStrBuilder() {
		return new BaseResultBuilder<String>();
	}
	
	/**
	 * 请求失败
	 * @param fail 失败内容
	 * @return
	 */
	public static BaseResult<String>  reqFail(String fail) {
		return new BaseResultBuilder<String>().fail(fail).build();
	}
	
	
}
