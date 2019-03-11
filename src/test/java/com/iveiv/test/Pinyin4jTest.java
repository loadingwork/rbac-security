package com.iveiv.test;

import org.junit.Test;

import com.iveiv.rbac.util.PinyinUtils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * 汉字转中文
 * @author irays
 *
 */
public class Pinyin4jTest {
	
	
	static HanyuPinyinOutputFormat outputFormat = new HanyuPinyinOutputFormat();
	
	@Test
	public void test1() throws BadHanyuPinyinOutputFormatCombination {
		 System.out.println(PinyinHelper.toHanYuPinyinString("呵呵...", outputFormat, "", true));
	}
	
	@Test
	public void test2() {
		
		System.out.println(PinyinUtils.getFirstSpell("safas我(&(&^&%!@"));
		System.out.println(PinyinUtils.getFullSpell("safas我(&(&^&%!@"));
		System.out.println(PinyinUtils.getFirstSpell("(&(&^&%!@"));
		
	}
	
	
	@Test
	public void test3() {
		
	}
	
	
	@Test
	public void test4() {
		
	}
	
	

}
