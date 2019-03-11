package com.iveiv.rbac.api.service.impl;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import com.iveiv.rbac.api.config.TokenAuthProperties;
import com.iveiv.rbac.api.domain.dto.JwtTokenDTO;
import com.iveiv.rbac.api.domain.dto.UserInfoDTO;
import com.iveiv.rbac.api.service.PersistentTokenService;
import com.iveiv.rbac.base.constant.RedisConst;
import com.iveiv.rbac.util.JsonBodyUtils;


/**
 * 
 * token持久化到redis中方案实现
 * 
 * @author irays
 *
 */
@Service("persistentTokenRedisServiceImpl")
public class PersistentTokenRedisServiceImpl implements PersistentTokenService {
	
	
	/**
	 * redis
	 */
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	
	/**
	 * token配置信息
	 */
	@Autowired
	private TokenAuthProperties tokenAuthProperties;

	@Override
	public void savePersistentTokenPO(JwtTokenDTO jwtTokenDTO, UserInfoDTO userInfoDTO) {
		
		if(jwtTokenDTO == null || userInfoDTO == null) {
			throw new RuntimeException("redis保存登录数据异常");
		}
		
		ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
		
		// 添加前缀
		String key = RedisConst.TOKEN_PROFIX + jwtTokenDTO.getPcode();
		
		opsForValue.set(key, JsonBodyUtils.toJSONString(userInfoDTO), 
				tokenAuthProperties.getInvalidIn(), TimeUnit.SECONDS);
		
	}

	@Override
	public UserInfoDTO getByPcode(String pcode) {
		
		ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
		
		String key = RedisConst.TOKEN_PROFIX + pcode;
		
		 String jsonUserInfoDTO = opsForValue.get(key);
		 
		 // 数据不存在
		 if(StringUtils.isEmpty(jsonUserInfoDTO)) {
			 return null;
		 }
		
		 return JsonBodyUtils.parseObject(jsonUserInfoDTO, UserInfoDTO.class);
	}

	
	
	
	
}
