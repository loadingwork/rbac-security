package com.iveiv.rbac.api.event;

import java.util.Date;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.iveiv.rbac.api.domain.dto.LoginSuccessDTO;
import com.iveiv.rbac.api.domain.dto.RequestHeaderDTO;
import com.iveiv.rbac.domain.po.UserAccountPO;

/**
 * 用户认证登录成功事件
 * 
 * @TODO 添加特殊处理
 * 
 * @author irays
 *
 */
public class UserLoginSuccessEvent extends AbstractTokenAuthEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1128473708398419801L;

	/**
	 * 用户登录成功发送到前端数据
	 */
	private final LoginSuccessDTO loginSuccessDTO;

	/**
	 * 账号信息
	 */
	private final UserAccountPO userAccountPO;

	/**
	 * 请求头
	 */
	private final RequestHeaderDTO requestHeaderDTO;

	public UserLoginSuccessEvent(UserAccountPO userAccountPO, RequestHeaderDTO requestHeaderDTO,
			LoginSuccessDTO loginSuccessDTO, Date authDate) {
		super(authDate);
		this.loginSuccessDTO = loginSuccessDTO;
		this.userAccountPO = userAccountPO;
		this.requestHeaderDTO = requestHeaderDTO;
	}

	public LoginSuccessDTO getLoginSuccessDTO() {
		return loginSuccessDTO;
	}

	public UserAccountPO getUserAccountPO() {
		return userAccountPO;
	}

	public RequestHeaderDTO getRequestHeaderDTO() {
		return requestHeaderDTO;
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
