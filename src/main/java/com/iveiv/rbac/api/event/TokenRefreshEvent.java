package com.iveiv.rbac.api.event;

import java.util.Date;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * token刷新事件
 * 
 * @TODO 添加特殊处理
 * 
 * @author irays
 *
 */
public class TokenRefreshEvent extends AbstractTokenAuthEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2110929159262550150L;

	private String token;

	private String newToken;

	public TokenRefreshEvent(Date authDate, String token, String newToken) {
		super(authDate);
		this.token = token;
		this.newToken = newToken;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
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
