package com.iveiv.test;

import org.joda.time.LocalDate;

/**
 * 时间测试
 * @author irays
 *
 */
public class Demo12 {
	
	public static void main(String[] args) {
		 LocalDate date = new LocalDate().minusYears(2);
		 LocalDate date2 = new LocalDate().plusYears(2);
		 
		 System.out.println(date);
		 System.out.println(date2);
	}

}
