package com.lgwork.config;

import static org.junit.Assert.assertNotNull;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.lgwork.RbacSecurityApplication;

/**
 * 数据源测试
 * @author irays
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes=RbacSecurityApplication.class)
public class DataSourceTest {
	
	@Autowired
	private DataSource datasource;
	
	
	@Test
	public void testNotNull() {
		
		assertNotNull(datasource);
		
		System.err.println(datasource);
		
	}
	
	
	

}
