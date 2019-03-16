package com.lgwork.aop;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import com.lgwork.base.constant.RedisConst;
import com.lgwork.domain.po.DictItemPO;
import com.lgwork.util.JsonBodyUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 数据字典缓存
 * 
 * DICT_CATEGORY_PREFIX  数据字典分类
 * 
 * DICT_TYPE_PREFIX  数据字典类型
 * 
 * 
 * DICT_CATEGORY_PREFIX + 数据字典分类编码  =  数据字典类型列表
 * DICT_CATEGORY_PREFIX + 数据字典类型编码  = 数据字典类型
 * 
 * DICT_TYPE_PREFIX + 数据字典类型编码 = 数据字典项列表
 * DICT_TYPE_PREFIX + 数据字典类型编码 + 数据字典项编码 = 数据字典项  (推荐)
 * DICT_TYPE_PREFIX + 数据字典项id = 数据字典项
 * 
 * @author irays
 *
 */
@Component
@Order(3)
@Slf4j
@Aspect
public class DictCacheAop {
	
	
	/**
	 *  redis缓存
	 */
	@Autowired
	private RedisTemplate<String, String>  redisTemplate;
	
	
	@Pointcut(value = "target(com.lgwork.sys.service.DictItemService)")
	private void dictItemServicePointcut() {
		
	}
	
	/**
	 * @param typeCode  数据字典类型
	 * @param dictCode  数据字典项编码
	 */
	@Pointcut(value = "execution(* getByTypeCodeAndDictCode(*,*)) && args(typeCode, dictCode)")
	private void getByTypeCodeAndDictCodePointcut(String typeCode, String dictCode) {
		
	}
	
	/**
	 * @param typeCode 数据字典类型
	 */
	@Pointcut(value = "execution(* listByTypeCode(*)) && args(typeCode)")
	private void listByTypeCodePointcut(String typeCode) {
		
	}
	
	/**
	 * 根据id查询
	 * @param id
	 */
	@Pointcut(value = "execution(* listByTypeCode(*)) && args(id)")
	private void getByIdPointcut(Long id) {
		
	}
	
	/**
	 * 清除缓存
	 */
	@Pointcut(value = "execution(* update*(..)) || execution(* delete*(..)) || execution(* save*(..))")
	private void dictItemEvictAllPointcut() {
		
	}
	
	
	@Around(value = "dictItemServicePointcut() && getByTypeCodeAndDictCodePointcut(typeCode, dictCode)", 
			argNames = "pjp,typeCode,dictCode")
	public Object getByTypeCodeAndDictCodeAdvice(ProceedingJoinPoint pjp, String typeCode, String dictCode) throws Throwable {
		
		// 获取key
		String key = getDictItemKey(typeCode, dictCode);
		
		ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
		
		if (key != null) {
			// 开始查询数据
			String result = opsForValue.get(key);
			log.debug("根据key: {}, 获取到结果: {}", key, result);
			
			if (StringUtils.isNotEmpty(result)) {
				// 反序列化
				DictItemPO dictItemPO = JsonBodyUtils.parseObject(result, DictItemPO.class);
				
				return dictItemPO;
			}
			
		}
		
		// 执行查询
		Object result = pjp.proceed();
		
		if (result != null && key != null) {
			// 保存
			String jsonBody = JsonBodyUtils.toJSONString(result);
			log.debug("根据key:{}, 添加缓存: {}", key, jsonBody);
			// 2个小时过期
			opsForValue.set(key, jsonBody, 2, TimeUnit.HOURS);
		}
		
		
		return result;
	}
	
	
	private String getDictItemKey(String typeCode, String dictCode) {
		
		if (StringUtils.isNoneEmpty(typeCode, dictCode)) {
			return null;
		}
		
		// 构建key
		StringBuffer sb = new StringBuffer();
		sb.append(RedisConst.DICT_TYPE_PREFIX);
		sb.append(typeCode);
		sb.append("-");
		sb.append(dictCode);
		String key =  sb.toString();
		log.debug("获取到key: {}", key);
		
		return key;
	}
	
	
	@Around(value = "dictItemServicePointcut() && listByTypeCodePointcut(typeCode)", 
			argNames = "pjp,typeCode")
	public Object  listByTypeCodeAdvice(ProceedingJoinPoint pjp, String typeCode) throws Throwable {
		
		String key = getSetDictItemKey(typeCode);
		
		ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
		
		if (key != null) {
			
			String result = opsForValue.get(key);
			
			log.debug("根据key: {}, 获取到结果: {}", key, result);
			
			if (StringUtils.isNotEmpty(result)) {
				List<DictItemPO> listDictItemPO = JsonBodyUtils.parseArray(result, DictItemPO.class);
				return listDictItemPO;
			}
			
		}
		
		// 执行
		Object result = pjp.proceed();
		
		if (result != null && key != null) {
			
			// 保存
			String jsonBody = JsonBodyUtils.toJSONString(result);
			log.debug("根据key:{}, 添加缓存: {}", key, jsonBody);
			// 2个小时过期
			opsForValue.set(key, jsonBody, 2, TimeUnit.HOURS);
			
		}
		
		
		return result;
	}
	
	/**
	 * 构建key
	 * @param typeCode
	 * @return
	 */
	private String getSetDictItemKey(String typeCode) {
		
		if (StringUtils.isEmpty(typeCode)) {
			return null;
		}
		
		// 构建key
		
		String key =  RedisConst.DICT_TYPE_PREFIX + typeCode;
		log.debug("获取到key: {}", key);
		
		return key;
	}
	
	
	/**
	 * 根据id查询切面
	 * @param pjp
	 * @param id
	 * @throws Throwable 
	 */
	@Around(value = "dictItemServicePointcut() && getByIdPointcut(id)", 
			argNames = "pjp,id")
	public Object getByIdAdvice(ProceedingJoinPoint pjp, Long id) throws Throwable {
		
		// 获取key
		String key = getKeyById(id);
		
		ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
		
		if (key != null) {
			// 开始查询数据
			String result = opsForValue.get(key);
			log.debug("根据key: {}, 获取到结果: {}", key, result);
			
			if (StringUtils.isNotEmpty(result)) {
				// 反序列化
				DictItemPO dictItemPO = JsonBodyUtils.parseObject(result, DictItemPO.class);
				
				return dictItemPO;
			}
			
		}
		
		// 执行查询
		Object result = pjp.proceed();
		
		if (result != null && key != null) {
			// 保存
			String jsonBody = JsonBodyUtils.toJSONString(result);
			log.debug("根据key:{}, 添加缓存: {}", key, jsonBody);
			// 2个小时过期
			opsForValue.set(key, jsonBody, 2, TimeUnit.HOURS);
		}
		
		
		return result;
		
	}
	
	/**
	 *  根据id拼接key
	 * @param id
	 * @return
	 */
	private String getKeyById(Long id) {
		if (id == null) {
			return null;
		}
		
		// 构建key
		String key =  RedisConst.DICT_TYPE_PREFIX + id;
		log.debug("获取到key: {}", key);
		
		return key;
	}
	
	
	/**
	 * @TODO  清除所有缓存
	 * 
	 */
	@Before(value = "dictItemServicePointcut() && dictItemEvictAllPointcut()")
	public void dictItemEvictAllAdvice() {
		// 清除所有缓存
		clearAll();
	}
	
	
    /**
     * 清除所有缓存
     */
    public void clearAll() {
    	redisTemplate.delete(RedisConst.DICT_TYPE_PREFIX + "*");
    }
    
    /**
     * 清除字典项缓存
     * @param typeCode
     */
    public void clearItemAll(String typeCode) {
    	// 删除
    	redisTemplate.delete(RedisConst.DICT_TYPE_PREFIX + typeCode + "-*");
    }
    /**
     * 删除指定数据字典项
     * @param typeCode
     * @param dictCode
     */
    public void clearDictCode(String typeCode, String dictCode) {
    	// 删除
    	redisTemplate.delete(RedisConst.DICT_TYPE_PREFIX + typeCode + "-" + dictCode);
    }
    
    /**
     * 清除指定数据字典项列表
     * @param typeCode
     */
    public void cleanListItemAll(String typeCode) {
    	redisTemplate.delete(RedisConst.DICT_TYPE_PREFIX + typeCode);
    }
    
    
	
	

}
