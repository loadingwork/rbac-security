package com.lgwork.test;

import java.util.regex.Pattern;

import org.junit.Test;

public class MatchTest {
	
	
	@Test
	public void test1() {
		
		Pattern matchCode = Pattern.compile("^[a-zA-Z]{1}[a-zA-Z1-9_]{3,16}$");
		
		
		System.err.println(matchCode.matcher("aa_aa_").matches());
		
	}
	

}
