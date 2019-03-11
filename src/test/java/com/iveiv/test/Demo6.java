package com.iveiv.test;

import java.util.Comparator;
import java.util.stream.Stream;

import org.junit.Test;

/**
 * 
 * 使用Optional可以在没有值时指定一个返回值，例如
 * 
 * @author irays
 *
 */
public class Demo6 {
	
	@Test
	public void testOptional2() {
	    Integer[] arr = new Integer[]{4,5,6,7,8,9};
	    Integer result = Stream.of(arr).filter(x->x>9).max(Comparator.naturalOrder()).orElse(-1);
	    System.out.println(result);
	    Integer result1 = Stream.of(arr).filter(x->x>9).max(Comparator.naturalOrder()).orElseGet(()->-1);
	    System.out.println(result1);
	    Integer result2 = Stream.of(arr).filter(x->x>9).max(Comparator.naturalOrder()).orElseThrow(RuntimeException::new);
	    System.out.println(result2);
	}

}
