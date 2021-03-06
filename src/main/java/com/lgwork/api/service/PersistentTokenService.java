package com.lgwork.api.service;

import com.lgwork.api.domain.dto.JwtTokenDTO;
import com.lgwork.api.domain.dto.UserInfoDTO;


/**
 * token 持久化服务接口
 * @author irays
 *
 */
public interface PersistentTokenService {
	
	
	
	/**
	 * 
	 * 保存token
	 * 
	 * @param jwtTokenDTO
	 * @param userInfoDTO
	 */
	void savePersistentTokenPO(JwtTokenDTO jwtTokenDTO, UserInfoDTO userInfoDTO);
	
	
	/**
	 * 根据储存编码获取数据
	 * @param pcode
	 * @return
	 */
	UserInfoDTO  getByPcode(String pcode);
	

}
