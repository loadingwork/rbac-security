package com.iveiv.rbac.util;

import static org.junit.Assert.*;

import org.junit.Test;


/**
 * join测试
 * @author irays
 *
 */
public class JoinerUtilsTest {

	@Test
	public void testStrArrJoinerArr() {
		
		String actual = JoinerUtils.strArrJoinerArr("1111,1111");
		assertEquals("1111,1111", actual);
	}
	
	@Test
	public void testStrArrJoinerArr2() {
		
		String actual = JoinerUtils.strArrJoinerArr("1111,1111", "aaa");
		assertEquals("1111,1111,aaa", actual);
	}
	
	@Test
	public void testStrArrJoinerArr3() {
		
		String actual = JoinerUtils.strArrJoinerArr("1111,1111", "aaa", "bbb", "ccc");
		
		assertEquals("1111,1111,aaa,bbb,ccc", actual);
	}
	
	
	@Test
	public void testStrArrJoinerArr4() {
		
		String actual = JoinerUtils.strArrJoinerArr("", "aaa", "bbb", "ccc");
		assertEquals("aaa,bbb,ccc", actual);
	}
	

	@Test
	public void testSplit() {
	}

}
