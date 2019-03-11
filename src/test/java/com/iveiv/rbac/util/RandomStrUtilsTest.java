package com.iveiv.rbac.util;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class RandomStrUtilsTest {

	@Test
	public void testGenCommonsStr() {
		
		long start = System.currentTimeMillis();
		
		Set<String>  set = new HashSet<>();
		
		for(int i =0; i<5000000; i++) {
			String genCommonsStrOld = RandomStrUtils.genCommonsStr(RandomStrUtils.NUMBER, 8, false);
			set.add(genCommonsStrOld);
		}
		
		long end = System.currentTimeMillis();
//#500000		 用时:6145  数量: 498750
//#500000		用时:6474  数量: 498776
//#5000000		用时:64111  数量: 4877363
		System.err.println("用时:" +(end - start) + "  数量: " + set.size());
		
	}

	@Test
	public void testGenCommonsStrOld() {
		long start = System.currentTimeMillis();
		
		Set<String>  set = new HashSet<>();
		
		for(int i =0; i<5000000; i++) {
			@SuppressWarnings("deprecation")
			String genCommonsStrOld = RandomStrUtils.genCommonsStrOld(RandomStrUtils.NUMBER, 8, false);
			set.add(genCommonsStrOld);
		}
		
		long end = System.currentTimeMillis();
		
		// 用时:6189  数量: 498650
		// 用时:6371  数量: 498664
//#5000000		用时:65098  数量: 4864177
		System.err.println("用时:" +(end - start) + "  数量: " + set.size());
	}

}
