package com.iveiv.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;

import com.iveiv.rbac.RbacSecurityApplication;

/**
 * 测试系统环境变量
 * @author irays
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes=RbacSecurityApplication.class)
public class EnvironmentTest {
	
	@Autowired
	private Environment environment;
	
	
	@Test
	public void environmentGetTest() {
		System.err.println(environment.getProperty("management.admin.name"));
		System.err.println(environment.getProperty("management.admin.password"));
	}
	
	

}
