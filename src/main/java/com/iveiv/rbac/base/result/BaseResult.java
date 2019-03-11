package com.iveiv.rbac.base.result;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * rest接口响应结果
 * 
 * @author irays
 *
 */
@ApiModel("返回结果")
@JsonInclude(Include.NON_NULL)
public class BaseResult<T> implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3551903937383806307L;

	/**
	 * 错误码 0 默认是成功
	 */
	@ApiModelProperty(value="错误码" , example="0")
	private String errcode;
	/**
	 * 错误信息
	 */
	@ApiModelProperty(value="错误信息" , example="ok")
	private String errmsg;
	/**
	 * 返回数据
	 */
	@ApiModelProperty(value="数据实体")
	private T data;

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

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	
	
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, new String[0]);
	}
	
	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj, new String[0]);
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	

}
