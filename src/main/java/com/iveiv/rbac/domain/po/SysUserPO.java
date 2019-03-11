package com.iveiv.rbac.domain.po;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.iveiv.rbac.base.BasePO;
import com.iveiv.rbac.base.jpa.convert.BoolConvert;

import lombok.Getter;
import lombok.Setter;


/**
 * 系统账户
 * @author irays
 *
 */
@Table(name="sys_user")
@Entity
@Setter
@Getter
public class SysUserPO extends BasePO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 账号编码
	 */
	@NotNull
	@Column(name="ucode", length=64, unique=true, nullable=false )
	private String ucode;
	
	/**
	 *  系统账户编码
	 */
	@NotNull
	@Column(name="sys_user_code", length=64, unique=true, nullable=false )
	private String sysUserCode;
	
	/**
	 * 昵称
	 */
	@Column(name="nickname", length=100)
	private String nickname;
	
	/**
	 * 电话号码
	 */
	@Column(name="telephone", length=32)
	private String telephone;
	
	/**
	 * 最后登录时间
	 */
	@Column(name="last_login_time")
	private Date lastLoginTime;
	
	/**
	 * 创建方式
	 */
	@NotNull
	@Column(name="type_create", length=12, nullable=false)
	private String typeCreate;
	
	/**
	 * 激活方式
	 */
	@NotNull
	@Column(name="activity_by", length=12, nullable=false)
	private String activityBy;
	
	/**
	 * 性别
	 */
	@NotNull
	@Column(name="gender",  length=5, nullable=false)
	private Integer gender;
	
	/**
	 * 年龄
	 */
	@NotNull
	@Column(name="age",  length=11, nullable=false)
	private Integer age;
	
	/**
	 * 出生年月日
	 */
	@Column(name="birthday")
	private Date birthday;
	
	/**
	 * 头像
	 */
	@Column(name="avatar_src", length=64)
	private String avatarSrc;
	
	/**
	 * 使用语言
	 */
	@NotNull
	@Column(name="language", length=12, nullable=false)
	private String language;
	
	/**
	 * 是否锁定
	 */
	@Convert(converter=BoolConvert.class)
	@NotNull
	@Column(name="is_locked", length=5, nullable=false)
	private Boolean locked;
	
	/**
	 * 是否激活
	 */
	@Convert(converter=BoolConvert.class)
	@NotNull
	@Column(name="is_activated", length=5, nullable=false)
	private Boolean activated;
	
	/**
	 * 是否可用
	 */
	@Convert(converter=BoolConvert.class)
	@NotNull
	@Column(name="is_enabled", length=5, nullable=false)
	private Boolean enabled;
	

	

}
