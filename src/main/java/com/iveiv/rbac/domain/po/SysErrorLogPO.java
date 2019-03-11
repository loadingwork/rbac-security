package com.iveiv.rbac.domain.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.iveiv.rbac.base.BasePO;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import lombok.Getter;
import lombok.Setter;

/**
 * 系统错误日记
 * @author irays
 *
 */
@Table(name = "sys_error_log")
@Entity
@Setter
@Getter
public class SysErrorLogPO  extends BasePO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2231528360956856632L;
	
	/**
	 * 操作系统类型
	 */
	@Enumerated(EnumType.STRING)
	@Column(name="operating_system", length=32)
	private OperatingSystem operatingSystem;
	/**
	 * 浏览器类型
	 */
	@Enumerated(EnumType.STRING)
	@Column(name="browser", length=32)
	private Browser browser;
	
	/**
	 * 客户端ip
	 */
	@Column(name="client_ip_address", length=64)
	private String clientIpAddress;
	
	/**
	 * 访问的url
	 */
	@Column(name="url", length=500)
	private String url;
	/**
	 * 请求方式
	 */
	@Column(name="http_method", length=16)
	private String httpMethod;
	/**
	 * 报错信息
	 */
	@Column(name="errmsg", length=500)
	private String errmsg;
	/**
	 * 用户名
	 */
	@Column(name="username", length=100)
	private String username;
	

}
