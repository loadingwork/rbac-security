package com.iveiv.rbac.base;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @MappedSuperclass 不能有@entity @table注解, 属性映射到子类中
 * 
 * @author irays
 *
 */
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@MappedSuperclass
public abstract class BasePO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 *   主键自增
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;
	
	/**
	 * 创建时间
	 */
	@NotNull
	@CreatedDate
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="gmt_create", nullable=false)
	private Date gmtCreate;
	
	/**
	 * 修改时间
	 */
	@LastModifiedDate
	@Column(name="gmt_modified")
	private Date gmtModified;

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
	
	/**
	 * 初始化数据
	 * @param base
	 */
	@Deprecated
	public static void initData(BasePO base) {
		initData(base, new Date());
	}
	
	@Deprecated
	public static void initData(BasePO base, Date date) {
		base.setGmtCreate(date);
	}
	
	@Deprecated
	public static void updateData(BasePO base) {
		updateData(base, new Date());
	}
	
	@Deprecated
	public static void updateData(BasePO base, Date date) {
		base.setGmtModified(date);
	}
	

}
