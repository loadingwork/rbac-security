package com.lgwork.base;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.lgwork.base.result.BaseResultBuilder;
import com.lgwork.enums.ErrorCodeEnum;

/**
 * 基本结果测试
 * @author irays
 *
 */
public class BaseResultTest {

	@Test
	public void test() {
		assertNotNull(new BaseResultBuilder<String>().enums(ErrorCodeEnum.SYS_ERROR).build());
	}
	
	
	@Test
	public void testEnumMsg() {
		assertEquals(ErrorCodeEnum.SYS_ERROR.getErrmsg(), new BaseResultBuilder<String>().enums(ErrorCodeEnum.SYS_ERROR).build().getErrmsg());
	}
	
	@Test
	public void testEnumCode() {
		assertEquals(ErrorCodeEnum.SYS_ERROR.getErrcode(), new BaseResultBuilder<String>().enums(ErrorCodeEnum.SYS_ERROR).build().getErrcode());
	}
	
	
	@Test
	public void testWithEnumAndWithMsg() {
		// 测试msg
		assertEquals("111", new BaseResultBuilder<String>().errmsg("111").enums(ErrorCodeEnum.SYS_ERROR).build().getErrmsg());
		// 测试code
		assertEquals(ErrorCodeEnum.SYS_ERROR.getErrcode(), new BaseResultBuilder<String>().errmsg("111").enums(ErrorCodeEnum.SYS_ERROR).build().getErrcode());
	}
	
	
	@Test
	public void testWithFail() {
		assertEquals("1", new BaseResultBuilder<String>().fail("111").build().getErrcode());
		assertEquals("111", new BaseResultBuilder<String>().fail("111").build().getErrmsg());
	}
	
	

}

