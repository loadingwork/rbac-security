package com.lgwork.api.service.impl;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgwork.api.config.TokenAuthProperties;
import com.lgwork.api.dao.PersistentTokenRememberMeDao;
import com.lgwork.api.service.PersistentTokenRememberMeService;
import com.lgwork.base.constant.RedisConst;
import com.lgwork.domain.po.PersistentTokenRememberMePO;
import com.lgwork.util.JsonBodyUtils;


/**
 * 
 *  持久化rememberMe凭证到redis方案实现
 * 
 * @author irays
 *
 */
@Service("persistentTokenRememberMeRedisServiceImpl")
public class PersistentTokenRememberMeRedisServiceImpl implements PersistentTokenRememberMeService {

	
	/**
	 * 持久化记住密码信息
	 */
	@Autowired
	private PersistentTokenRememberMeDao persistentTokenRememberMeDao;
	
	/**
	 * redis操作
	 */
	@Autowired
	private RedisTemplate<String, String>  redisTemplate;
	
	/**
	 * token配置信息
	 */
	@Autowired
	private TokenAuthProperties tokenAuthProperties;
	
	
	@Transactional(rollbackFor=Exception.class)
	@Override
	public PersistentTokenRememberMePO savePersistentTokenRememberMePO(PersistentTokenRememberMePO persistentTokenRememberMePO) {
		
		if(persistentTokenRememberMePO == null) {
			throw new RuntimeException("数据异常");
		}
		
		// 持久化到数据库中
		persistentTokenRememberMePO = persistentTokenRememberMeDao.save(persistentTokenRememberMePO);
		
		// 持久化到数据库中
		ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
		
		// key
		String key = RedisConst.REMEMBER_ME_PROFIX + persistentTokenRememberMePO.getPcode();
		
		// 设置
		opsForValue.set(key, JsonBodyUtils.toJSONString(persistentTokenRememberMePO), 
				tokenAuthProperties.getRememberMeInvalidIn(), TimeUnit.SECONDS);
		
		return persistentTokenRememberMePO;
	}

	@Override
	public PersistentTokenRememberMePO getByPcode(String pcode) {
		
		ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
		
		// key
		String key = RedisConst.REMEMBER_ME_PROFIX + pcode;
		
		// 获取参数
		String value = opsForValue.get(key);
		
		if(StringUtils.isEmpty(value)) {
			return null;
		}
		
		return JsonBodyUtils.parseObject(value, PersistentTokenRememberMePO.class);
	}
	
	
	
	
	

}
