package com.iveiv.rbac.domain.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;

import lombok.Getter;
import lombok.Setter;

/**
 * 二维码图片返回实体
 * @author irays
 *
 */
@Getter
@Setter
public class QrcodeImgRespDTO {
	
	/**
	 * 访问地址
	 */
	private String url;
	/**
	 * 编码
	 */
	private String code;
	
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
