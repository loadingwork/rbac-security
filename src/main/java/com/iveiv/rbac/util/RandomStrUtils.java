package com.iveiv.rbac.util;

import java.util.stream.Stream;

import org.apache.commons.rng.UniformRandomProvider;
import org.apache.commons.rng.simple.RandomSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 随机数工具类
 * 默认不以0开头
 * @author irays
 *
 */
public class RandomStrUtils {
	
	/**
	 * 日记工具
	 */
	private static final Logger log = LoggerFactory.getLogger(RandomStrUtils.class);
	
	private final static UniformRandomProvider rng = RandomSource.create(RandomSource.MT);
	
	/**
	 * 纯数字
	 */
	public static final String NUMBER = "0123456789";
	/**
	 * 纯小写字母
	 */
	public static final String LETTER = "abcdefghijklmnopqrstuvwxyz";
	/**
	 * 纯大写字母
	 */
	public static final String LETTER_UPPER_CASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	/**
	 * 指定字符
	 */
	public static final String SPECIAL_CHAR = "!@#$%^&*()_+";
	/**
	 * 所有的字符串
	 */
	public static final String ALL_CHAR_STR = NUMBER + LETTER + LETTER_UPPER_CASE + SPECIAL_CHAR;
	/**
	 * 数字和字母区分大小写
	 */
	public static final String ALL_CHAR = NUMBER + LETTER + LETTER_UPPER_CASE;
	/**
	 * 数字和字母不区分大小写
	 */
	public static final String ALL_CHAR_LOW = NUMBER + LETTER;
	
	
	
	/**
	 * 生产随机字符串
	 * @param str 源随机字符串
	 * @param length  长度
	 * @param isFirstNum  第一位是否为数字
	 * @return
	 */
	public static String genCommonsStr(String str, int length, boolean isFirstNum) {
		// 固定长度
		final int numberLength = str.length();
		
		StringBuffer randomStr = Stream.generate(() -> rng.nextInt(numberLength)).limit(length).map(item -> {
			char charAt = str.charAt(item);
			return charAt;
		}).reduce(new StringBuffer(), (sb, s) -> sb.append(s), StringBuffer::append);
		
//		StringBuffer sb = new StringBuffer();
//		for (int i = 0; i < length; i++) {
//			int ranInt = rng.nextInt(numberLength);
//			// 第一位判断
//			if (i == 0 && ranInt == 0 && !isFirstNum) {
//				// 不允许第一位为0处理
//				i = -1;
//				continue;
//			}
//			sb.append(str.charAt(ranInt));
//		}
		
		if (isFirstNum && randomStr.charAt(0) == 0) {
			// 替换第一位数
			int ranNum = rng.nextInt(numberLength - 1);
			char charAt = str.substring(1).charAt(ranNum);
			randomStr.setCharAt(0, charAt);
		}
		
		String result = randomStr.toString();
		
		log.debug("随机数为: {}" , result);
		
		return result;
	}
	
	
	/**
	 * 老的
	 * @param str
	 * @param length
	 * @param isFirstNum
	 * @return
	 */
	@Deprecated
	public static String genCommonsStrOld(String str, int length, boolean isFirstNum) {
		// 固定长度
		final int numberLength = str.length();
		
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int ranInt = rng.nextInt(numberLength);
			// 第一位判断
			if (i == 0 && ranInt == 0 && !isFirstNum) {
				// 不允许第一位为0处理
				i = -1;
				continue;
			}
			sb.append(str.charAt(ranInt));
		}
		
		String result = sb.toString();
		
		log.debug("随机数为: {}", result);
		
		return result;
	}
	
	
	/**
	 * 返回纯数字
	 * @param length
	 * @param isFirstNum
	 * @return
	 */
	public static String genIntStr(int length, boolean isFirstNum) {
		return genCommonsStr(NUMBER, length, isFirstNum);
	}
	
	/**
	 * 默认首个字母不为0
	 * @param length
	 * @return
	 */
	public static String genIntStr(int length) {
		return genIntStr(length, false);
	}
	
	/**
	 * 区分随机数
	 * @param length
	 * @param isFirstNum
	 * @return
	 */
	public static String genCaseStr(int length, boolean isFirstNum) {
		
		String result = genCommonsStr(ALL_CHAR, length, isFirstNum);
		
		return result;
	}
	
	/**
	 * 小写
	 * @param length
	 * @return
	 */
	public static String genLowerStr(int length) {
		return genCaseStr(length, false).toLowerCase();
	}
	
	/**
	 * 大写
	 * @param length
	 * @return
	 */
	public static String genUpperStr(int length) {
		return genCaseStr(length, false).toUpperCase();
	}
	
	
	

}
