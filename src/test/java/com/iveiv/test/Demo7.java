package com.iveiv.test;

import java.util.Optional;

import org.junit.Test;

/**
 * Optional的创建
	采用Optional.empty()创建一个空的Optional，使用Optional.of()创建指定值的Optional。同样也可以调用Optional对象的map方法进行Optional的转换，调用flatMap方法进行Optional的迭代
 * @author irays
 *
 */
public class Demo7 {
	
	@Test
	public void testStream1() {
//	    Optional<Student> studentOptional = Optional.of(new Student("user1",21));
//	    Optional<String> optionalStr = studentOptional.map(Student::getName);
//	    System.out.println(optionalStr.get());
	}
	 
	public static Optional<Double> inverse(Double x) {
	    return x == 0 ? Optional.empty() : Optional.of(1 / x);
	}
	 
	public static Optional<Double> squareRoot(Double x) {
	    return x < 0 ? Optional.empty() : Optional.of(Math.sqrt(x));
	}
	 
	/**
	 * Optional的迭代
	 */
	@Test
	public void testStream2() {
//	    double x = 4d;
//	    Optional<Double> result1 = inverse(x).flatMap(StreamTest7::squareRoot);
//	    result1.ifPresent(System.out::println);
//	    Optional<Double> result2 = Optional.of(4.0).flatMap(StreamTest7::inverse).flatMap(StreamTest7::squareRoot);
//	    result2.ifPresent(System.out::println);
	}

}
