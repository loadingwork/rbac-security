package com.lgwork.api.event;

import java.util.Date;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 记住密码登录事件
 * 
 * @TODO 添加特殊处理
 * 
 * @author irays
 *
 */
public class RememberMeLoginSuccessEvent extends AbstractTokenAuthEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6649615360958072515L;

	private String rememberMeValue;

	private String newToken;

	public RememberMeLoginSuccessEvent(Date authDate, String rememberMeValue, String newToken) {
		super(authDate);
		this.rememberMeValue = rememberMeValue;
		this.newToken = newToken;
	}

	public String getRememberMeValue() {
		return rememberMeValue;
	}

	public void setRememberMeValue(String rememberMeValue) {
		this.rememberMeValue = rememberMeValue;
	}

	public String getNewToken() {
		return newToken;
	}

	public void setNewToken(String newToken) {
		this.newToken = newToken;
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
