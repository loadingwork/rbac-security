package com.iveiv.rbac.base.constant;

import static org.junit.Assert.assertEquals;

import org.junit.Test;



/**
 * 测试正则表达式
 * @author irays
 *
 */
public class PatternValidateTest {

	
	/**
	 * 测试邮箱
	 */
	@Test
	public void testEmail() {
		assertEquals(true, PatternValidate.EMAIL.matcher("irayslu@qq.com").matches());
	}
	
	
	@Test
	public void testUrl() {
		assertEquals(true, PatternValidate.URL.matcher("http://www.baidu.com").matches());
	}
	
	
	@Test
	public void testDataIso() {
		
	}
	

}
