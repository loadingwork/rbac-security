package com.iveiv.rbac.domain.po;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.iveiv.rbac.base.BasePO;
import com.iveiv.rbac.base.jpa.convert.BoolConvert;

import lombok.Getter;
import lombok.Setter;




/**
 * 随机字符串工具类
 * @author irays
 *
 */
@Table(name = "sys_random_str")
@Entity
@Setter
@Getter
public class SysRandomStrPO  extends BasePO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -249222453443994262L;
	
	/**
	 * 内容
	 */
	@Column(name="content", length=64, nullable=false)
	private String content;
	/**
	 * 长度
	 */
	@Column(name="length")
	private Integer length;
	/**
	 * 类型
	 * random = r
	 */
	@Column(name="type", length=32, nullable=false)
	private String type;
	/**
	 * 过期时间
	 * 
	 * used  = 1  将会失效
	 * 
	 */
	@Column(name = "expires_at")
	private Date expiresAt;
	/**
	 * 是否使用
	 */
	@Convert(converter=BoolConvert.class)
	@Column(name="is_used", length=5, nullable=false)
	private Boolean used;
	
	
	

}
