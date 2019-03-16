package com.lgwork.domain.dto;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.http.ResponseEntity;

import lombok.Getter;
import lombok.Setter;


/**
 * 
 * 结果返回
 * 
 * @author irays
 *
 */
@Setter
@Getter
public class SandboxAppMessageRespDTO implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4663897029974822866L;

	private ResponseEntity<?> resp;
	
	private String  decodeResult;

	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	

}
