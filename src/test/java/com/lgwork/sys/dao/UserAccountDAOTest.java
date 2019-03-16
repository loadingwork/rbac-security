package com.lgwork.sys.dao;

import static org.junit.Assert.fail;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.lgwork.RbacSecurityApplication;
import com.lgwork.domain.po.UserAccountPO;

/**
 * 
 * 用户账号持久化接口测试类
 * 
 * @author irays
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes=RbacSecurityApplication.class)
public class UserAccountDAOTest {
	
	/**
	 * 用户账号持久化接口
	 */
	@Autowired
	private UserAccountDAO userAccountDAO;
	
	@Test
	public void testSave() {
		
		Date date = new Date();
		
		UserAccountPO userAccount = new UserAccountPO();
		
		userAccount.setGmtCreate(date);
		userAccount.setUcode(UUID.randomUUID().toString());
		userAccount.setUsername("admin");
		userAccount.setPassword("123456");
		userAccount.setLoginPwdEncryption("a1");
		userAccount.setEnabled(true);
		
		userAccountDAO.save(userAccount);
	}
	

	@Test
	public void testFindAll() {
		List<UserAccountPO> findAll = userAccountDAO.findAll();
		System.out.println(findAll);
	}


	@Test
	public void testFindById() {
		fail("Not yet implemented");
	}

}
