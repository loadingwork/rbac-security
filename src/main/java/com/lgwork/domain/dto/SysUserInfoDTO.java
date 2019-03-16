package com.lgwork.domain.dto;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * 系统后台用户dto模型
 * 
 * @author irays
 *
 */
@Setter
@Getter
public class SysUserInfoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3203425995767932204L;

	/**
	 * 账号
	 */
	private String username;

	/**
	 * 手机号
	 */
	private String phone;

	/**
	 * 邮箱
	 */
	private String email;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 盐值
	 */
	private String salt;

	/**
	 * 第三方openId
	 */
	private String thirdPartyOpenId;

	/**
	 * 第三方平台
	 */
	private String thirdPartyOauthPlateform;
	/**
	 * 第三方unionid
	 */
	private String thirdPartyUnionId;

	/**
	 * 加密方式
	 */
	private String loginPwdEncryption;

	/**
	 * 系统用户id
	 */
	private Long id;

	/**
	 * 账号编码
	 */
	private String ucode;

	/**
	 * 系统账户编码
	 */
	private String sysUserCode;

	/**
	 * 昵称
	 */
	private String nickname;

	/**
	 * 电话号码
	 */
	private String telephone;

	/**
	 * 最后登录时间
	 */
	private Date lastLoginTime;

	/**
	 * 创建方式
	 */
	private String typeCreate;

	/**
	 * 激活方式
	 */
	private String activityBy;

	/**
	 * 性别
	 */
	private Integer gender;

	/**
	 * 年龄
	 */
	private Integer age;

	/**
	 * 出生年月日
	 */
	private Date birthday;

	/**
	 * 头像
	 */
	private String avatarSrc;

	/**
	 * 使用语言
	 */
	private String language;

	/**
	 * 是否锁定
	 */
	private Boolean locked;

	/**
	 * 是否激活
	 */
	private Boolean activated;

	/**
	 * 是否可用
	 */
	private Boolean enabled;
	
	/**
	 * 创建时间
	 */
	private Date gmtCreate;
	
	/**
	 * 修改时间
	 */
	private Date gmtModified;
	
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	

}
