package com.iveiv.test;

import java.util.stream.Stream;

import org.junit.Test;

/**
 * 6.并行流
可以将普通顺序执行的流转变为并行流，只需要调用顺序流的parallel() 方法即可，如Stream.iterate(1, x -> x + 1).limit(10).parallel()。
1） 并行流的执行顺序
我们调用peek方法来瞧瞧并行流和串行流的执行顺序，peek方法顾名思义，就是偷窥流内的数据，peek方法声明为Stream<T> peek(Consumer<? super T> action);加入打印程序可以观察到通过流内数据，见如下代码：
 * @author irays
 *
 */
public class Demo10 {
	
	public void peek1(int x) {
        System.out.println(Thread.currentThread().getName() + ":->peek1->" + x);
    }
 
    public void peek2(int x) {
        System.out.println(Thread.currentThread().getName() + ":->peek2->" + x);
    }
 
    public void peek3(int x) {
        System.out.println(Thread.currentThread().getName() + ":->final result->" + x);
    }
 
    /**
     * peek，监控方法
     * 串行流和并行流的执行顺序
     */
    @org.junit.Test
    public void testPeek() {
        Stream<Integer> stream = Stream.iterate(1, x -> x + 1).limit(10);
        stream.peek(this::peek1).filter(x -> x > 5)
                .peek(this::peek2).filter(x -> x < 8)
                .peek(this::peek3)
                .forEach(System.out::println);
    }
 
    @Test
    public void testPeekPal() {
        Stream<Integer> stream = Stream.iterate(1, x -> x + 1).limit(10).parallel();
        stream.peek(this::peek1).filter(x -> x > 5)
                .peek(this::peek2).filter(x -> x < 8)
                .peek(this::peek3)
                .forEach(System.out::println);
    }

}
