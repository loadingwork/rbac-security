package com.lgwork.api.event;

import java.util.Date;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 记住密码, 密钥刷新事件
 * 
 * @TODO 添加特殊处理
 * @author irays
 *
 */
public class RememberMeRefreshEvent extends AbstractTokenAuthEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7215339686778596385L;

	private String rememberMeValue;

	private String newRememberMeValue;

	public RememberMeRefreshEvent(Date authDate, String rememberMeValue, String newRememberMeValue) {
		super(authDate);

		this.rememberMeValue = rememberMeValue;
		this.newRememberMeValue = newRememberMeValue;
	}

	public String getRememberMeValue() {
		return rememberMeValue;
	}

	public void setRememberMeValue(String rememberMeValue) {
		this.rememberMeValue = rememberMeValue;
	}

	public String getNewRememberMeValue() {
		return newRememberMeValue;
	}

	public void setNewRememberMeValue(String newRememberMeValue) {
		this.newRememberMeValue = newRememberMeValue;
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
