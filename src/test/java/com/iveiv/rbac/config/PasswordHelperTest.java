package com.iveiv.rbac.config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.iveiv.rbac.RbacSecurityApplication;
import com.iveiv.rbac.config.ext.PasswordHelper;
import com.iveiv.rbac.util.UuidUtil;


/**
 * 加密辅助工具测试
 * @author irays
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes=RbacSecurityApplication.class)
public class PasswordHelperTest {
	
	
	/**
	 * 密码辅助类
	 */
	@Autowired
	private PasswordHelper passwordHelper;

	@Test
	public void testPasswordHelper() {
		
		String salt = UuidUtil.uuid32();
		
		String rawPassword = "123456";
		
		String encode = passwordHelper.encode(rawPassword, salt);
		
		System.err.println("encode: " + encode + ", salt : " + salt);
		
		boolean matches = passwordHelper.matches(rawPassword, encode, salt);
		
		assertEquals(true, matches);
		
	}

	@Test
	public void testEncode() {
		fail("Not yet implemented");
	}

	@Test
	public void testMatches() {
		fail("Not yet implemented");
	}

}
