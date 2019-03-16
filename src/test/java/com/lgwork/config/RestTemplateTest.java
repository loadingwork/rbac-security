package com.lgwork.config;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.lgwork.RbacSecurityApplication;


/**
 * http测试
 * @author irays
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes=RbacSecurityApplication.class)
public class RestTemplateTest {
	
	@Autowired
	private RestTemplate restTemplate;
	
	
	@Test
	public void testNotNull() {
		assertNotNull(restTemplate);
	}
	
	
	

}
