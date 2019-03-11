package com.iveiv.test;

import org.junit.Test;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;

import com.iveiv.rbac.domain.po.SysUserPO;

public class Demo13 {
	
	
	@Test
	public void test1() {
		
		SysUserPO sysUserPO = new SysUserPO();

		ExampleMatcher matcher = ExampleMatcher.matching()     
		  .withIgnorePaths("lastname")                         
		  .withIncludeNullValues();                         

		Example<SysUserPO> example = Example.of(sysUserPO, matcher); 
		
		System.err.println(example);
		
	}
	
	public void test2() {
		
//		ExampleMatcher matcher = ExampleMatcher.matching()
//				  .withMatcher("firstname", endsWith(""))
//				  .withMatcher("lastname", startsWith().ignoreCase());
		
	}
	

}
