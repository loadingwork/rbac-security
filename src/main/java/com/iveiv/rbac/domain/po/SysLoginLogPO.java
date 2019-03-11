package com.iveiv.rbac.domain.po;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.iveiv.rbac.base.BasePO;
import com.iveiv.rbac.enums.SysLoginLogTypeEnum;

import lombok.Getter;
import lombok.Setter;


/**
 * 
 * @author irays
 *
 */
@Table(name="sys_login_log")
@Entity
@Setter
@Getter
public class SysLoginLogPO extends BasePO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6464305701012956531L;
	
	/**
	 * 登录事件
	 */
	private Date loginTime;
	/**
	 * 登录类型
	 * @Convert(converter = XXConverter.class)
	 */
	@Enumerated(EnumType.STRING)
	private SysLoginLogTypeEnum type;
	/**
	 * 用户令牌(用户名)
	 */
	@Column(name="username", length=100)
	private String username;

}
