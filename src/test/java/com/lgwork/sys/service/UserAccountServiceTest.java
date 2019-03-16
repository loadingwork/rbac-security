package com.lgwork.sys.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import com.lgwork.RbacSecurityApplication;
import com.lgwork.domain.po.UserAccountPO;

/**
 * 
 * @author irays
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes=RbacSecurityApplication.class)
public class UserAccountServiceTest {
	
	
	@Autowired
	private UserAccountService userAccountService;
	
	

	/**
	 * 
	 * 测试自定义dsl分页
	 * 
	 */
	@Test
	public void testPageSearchByCondition() {
		
		UserAccountPO userAccountPO = new UserAccountPO();
		
		userAccountPO.setUsername("admin");
		
		Page<UserAccountPO> pageSearchByCondition = userAccountService.pageSearchByCondition(1, 10, userAccountPO);
		
		System.err.println(pageSearchByCondition);
		
	}

}
