package com.lgwork.sys.service;

import com.lgwork.base.result.BaseResult;
import com.lgwork.domain.dto.QrcodeImgRespDTO;
import com.lgwork.domain.po.QrcodeImgPO;

/**
 * qrcode 二维码
 * 
 * @TODO 暂时时间不控制
 * 
 * @author irays
 *
 */
public interface QrcodeImgService {

	
	/**
	 * 保存二维码
	 * @param width
	 * @param height
	 * @param content
	 * @return
	 */
	BaseResult<QrcodeImgRespDTO> saveQrcodeImgPO(Integer width, Integer height, String content) throws Exception;
	
	/**
	 * 根据code获取
	 * @param code
	 * @return
	 */
	QrcodeImgPO findBycode(String code);
	

}
