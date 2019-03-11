package com.iveiv.rbac.sys.service;

import com.iveiv.rbac.base.result.BaseResult;
import com.iveiv.rbac.domain.dto.QrcodeImgRespDTO;
import com.iveiv.rbac.domain.po.QrcodeImgPO;

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
