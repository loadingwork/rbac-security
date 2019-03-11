package com.iveiv.rbac.api.event;

import java.util.Date;

import org.springframework.context.ApplicationEvent;

/**
 * token认证, 授权事件
 * 
 * @author irays
 *
 */
public abstract class AbstractTokenAuthEvent extends ApplicationEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1079504660478502858L;

	/**
	 * 系统枚举
	 */
	private final Date authDate;

	public AbstractTokenAuthEvent(Date authDate) {
		super(authDate);
		this.authDate = authDate;
	}

	public Date getAuthDate() {
		return authDate;
	}

	

}
