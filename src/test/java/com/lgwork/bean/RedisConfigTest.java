package com.lgwork.bean;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import com.lgwork.RbacSecurityApplication;



/**
 * redis连接测试
 * @author irays
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes=RbacSecurityApplication.class)
public class RedisConfigTest {
	
	
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	
	@Test
	public void testNull() {
		assertNotNull(redisTemplate);
		
		ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
		
		opsForValue.set("hello", "1111");
	}
	

}
