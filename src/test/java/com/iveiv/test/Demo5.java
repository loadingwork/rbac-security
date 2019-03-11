package com.iveiv.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;

/**
 * Optional类型
 * https://www.cnblogs.com/andywithu/p/7404101.html
 * @author irays
 *
 */
public class Demo5 {
	
	@Test
    public void testOptional() {
        List<String> list = new ArrayList<String>() {
            /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			{
                add("user1");
                add("user2");
            }
        };
        Optional<String> opt = Optional.of("andy with u");
        opt.ifPresent(list::add);
        list.forEach(System.out::println);
    }

}
