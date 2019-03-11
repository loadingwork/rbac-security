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
 *    二维码储存
 *    生成与识别库
 * @author irays
 *
 */
@Table(name="qrcode_img")
@Entity
@Setter
@Getter
public class QrcodeImgPO extends BasePO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8014783653984047866L;
	
	/**
	 * 编码
	 */
	@Column(name="code", length=32, nullable=false, unique=true)
	private String code;
	/**
	 * 文件key 
	 * 实际为文件本地访问相对路径
	 */
	@Column(name = "file_key", length = 500)
	private String fileKey;
	/**
	 * 内容
	 * 格式:
	 * 业务://编码
	 * 例如
	 * login://aaaaaa
	 */
	@Column(name="content", length=500)
	private String content;
	/**
	 * 类型
	 * login
	 * qr
	 */
	@Column(name="type", length=32, nullable=false)
	private String type;
	/**
	 * 过期时间
	 */
	@Column(name = "expires_at")
	private Date expiresAt;
	/**
	 * 是否使用
	 */
	@Convert(converter=BoolConvert.class)
	@Column(name="is_used",  nullable=false)
	private Boolean used;
	

}
