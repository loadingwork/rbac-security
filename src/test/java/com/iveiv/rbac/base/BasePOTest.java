package com.iveiv.rbac.base;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;



public class BasePOTest {
	
	private List<String> checkList = new ArrayList<String>();
	
	
	@Before
	public  void before() {
		checkList.add("And");
		checkList.add("Or");
		checkList.add("Is");
		checkList.add("Equals");
		checkList.add("Between");
		checkList.add("LessThan");
		checkList.add("LessThanEqual");
		checkList.add("GreaterThan");
		checkList.add("GreaterThanEqual");
		checkList.add("After");
		checkList.add("Before");
		checkList.add("IsNull");
		checkList.add("IsNotNull");
		checkList.add("NotNull");
		checkList.add("Like");
		checkList.add("NotLike");
		checkList.add("StartingWith");
		checkList.add("EndingWith");
		checkList.add("Containing");
		checkList.add("OrderBy");
		checkList.add("Not");
		checkList.add("In");
		checkList.add("NotIn");
		checkList.add("True");
		checkList.add("False");
		checkList.add("IgnoreCase");
	}

	@Test
	public void test() {
		
	}

}
