package com.iveiv.rbac.domain.po;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iveiv.rbac.base.BasePO;
import com.iveiv.rbac.base.jpa.convert.BoolConvert;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * 用户账号表
 * 
 * @author irays
 *
 */
@Table(name="user_account")
@Entity
@Setter
@Getter
public class UserAccountPO extends BasePO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 账号编码
	 * 
	 * 系统可以简化为ucode
	 * 其他所有表都不能使用
	 * 
	 */
	@NotNull
	@Column(name="ucode",  length=64, unique=true, nullable=false )
	private String ucode;
	
	/**
	 * 账号
	 */
	@NotNull
	@Column(name="username", length=100, nullable=false, unique=true)
	private String  username;
	
	/**
	 * 手机号
	 */
	@Column(name="phone",  length=32, unique=true)
	private String phone;
	
	/**
	 * 邮箱
	 */
	@Column(name="email",  length=50, unique=true)
	private String email;
	
	/**
	 * 密码
	 */
	@JsonIgnore
	@NotNull
	@Column(name="password",  length=100, nullable=false)
	private String password;
	
	/**
	 *  盐值
	 */
	@JsonIgnore
	@Column(name="salt",  length=100)
	private String salt;
	
	/**
	 * 第三方openId
	 */
	@Column(name="third_party_open_id",  length=64)
	private String thirdPartyOpenId;
	
	/**
	 * 第三方平台
	 */
	@Column(name="third_party_oauth_plateform",  length=12)
	private String thirdPartyOauthPlateform;
	/**
	 * 第三方unionid
	 */
	@Column(name="third_party_union_id",  length=64)
	private String thirdPartyUnionId;
	
	/**
	 * 加密方式
	 */
	@NotNull
	@Column(name="login_pwd_encryption", length=12, nullable=false)
	private String loginPwdEncryption;
	
	/**
	 * 
	 * 没有特殊情况不能使用这个
	 * 
	 * 账号是否可用
	 */
	@Convert(converter=BoolConvert.class)
	@NotNull
	@Column(name="is_enabled", length=5, nullable=false)
	private Boolean enabled;

	

}
