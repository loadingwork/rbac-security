package com.iveiv.test;

import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

import org.junit.Test;

public class Demo2 {
	
	
	@Test
	public void test1() {
//		创建无限流
		Stream.generate(()->"number"+new Random().nextInt()).limit(100).forEach(System.out::println);
		
	}
	
	@Test
	public void  test2() {
//		创建空的流
		 Stream<Integer> stream  = Stream.empty();
		 assertNotNull(stream);
	}
	
	
	@Test
	public void test3() {
//		创建规律的无限流
		Stream.iterate(0,x->x+1).limit(10).forEach(System.out::println);
	    Stream.iterate(0,x->x).limit(10).forEach(System.out::println);
	    //Stream.iterate(0,x->x).limit(10).forEach(System.out::println);与如下代码意思是一样的
	    Stream.iterate(0, UnaryOperator.identity()).limit(10).forEach(System.out::println);
	}
	
	@Test
	public void testFilter(){
	    Integer[] arr = new Integer[]{1,2,3,4,5,6,7,8,9,10};
	    Arrays.stream(arr).filter(x->x>3&&x<8).forEach(System.out::println);
	}
	
	/**
	 * flapMap：拆解流
	 */
	@Test
	public void testFlapMap1() {
	    String[] arr1 = {"a", "b", "c", "d"};
	    String[] arr2 = {"e", "f", "c", "d"};
	    String[] arr3 = {"h", "j", "c", "d"};
	   // Stream.of(arr1, arr2, arr3).flatMap(x -> Arrays.stream(x)).forEach(System.out::println);
	    Stream.of(arr1, arr2, arr3).flatMap(Arrays::stream).forEach(System.out::println);
	}
	
	String[] arr1 = {"abc","a","bc","abcd"};
	/**
	 * Comparator.comparing是一个键提取的功能
	 * 以下两个语句表示相同意义
	 */
	@Test
	public void testSorted1_(){
	    /**
	     * 按照字符长度排序
	     */
	    Arrays.stream(arr1).sorted((x,y)->{
	        if (x.length()>y.length())
	            return 1;
	        else if (x.length()<y.length())
	            return -1;
	        else
	            return 0;
	    }).forEach(System.out::println);
	    Arrays.stream(arr1).sorted(Comparator.comparing(String::length)).forEach(System.out::println);
	}
	 
	/**
	 * 倒序
	 * reversed(),java8泛型推导的问题，所以如果comparing里面是非方法引用的lambda表达式就没办法直接使用reversed()
	 * Comparator.reverseOrder():也是用于翻转顺序，用于比较对象（Stream里面的类型必须是可比较的）
	 * Comparator. naturalOrder()：返回一个自然排序比较器，用于比较对象（Stream里面的类型必须是可比较的）
	 */
	@Test
	public void testSorted2_(){
	    Arrays.stream(arr1).sorted(Comparator.comparing(String::length).reversed()).forEach(System.out::println);
	    Arrays.stream(arr1).sorted(Comparator.reverseOrder()).forEach(System.out::println);
	    Arrays.stream(arr1).sorted(Comparator.naturalOrder()).forEach(System.out::println);
	}
	 
	/**
	 * thenComparing
	 * 先按照首字母排序
	 * 之后按照String的长度排序
	 */
	@Test
	public void testSorted3_(){
	    Arrays.stream(arr1).sorted(Comparator.comparing(this::com1).thenComparing(String::length)).forEach(System.out::println);
	}
	public char com1(String x){
	    return x.charAt(0);
	}
	

}
