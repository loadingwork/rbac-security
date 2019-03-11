package com.iveiv.test;

import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;


/**
 * 提取流和组合流
 * https://www.cnblogs.com/andywithu/p/7404101.html
 * @author irays
 *
 */
public class Demo3 {
	
	
	String[] arr1, arr2, arr3;
	
	@Before
    public void init(){
        arr1 = new String[]{"a","b","c","d"};
        arr2 = new String[]{"d","e","f","g"};
        arr3 = new String[]{"i","j","k","l"};
    }
    /**
     * limit，限制从流中获得前n个数据
     */
    @Test
    public void testLimit(){
        Stream.iterate(1,x->x+2).limit(10).forEach(System.out::println);
    }
 
    /**
     * skip，跳过前n个数据
     */
    @Test
    public void testSkip(){
//        Stream.of(arr1).skip(2).limit(2).forEach(System.out::println);
        Stream.iterate(1,x->x+2).skip(1).limit(5).forEach(System.out::println);
    }
 
    /**
     * 可以把两个stream合并成一个stream（合并的stream类型必须相同）
     * 只能两两合并
     */
    @Test
    public void testConcat(){
        Stream<String> stream1 = Stream.of(arr1);
        Stream<String> stream2 = Stream.of(arr2);
        Stream.concat(stream1,stream2).distinct().forEach(System.out::println);
     }

}
