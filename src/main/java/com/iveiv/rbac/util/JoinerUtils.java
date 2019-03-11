package com.iveiv.rbac.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;

/**
 * 自定义拼接工具类
 * @author irays
 *
 */
public class JoinerUtils {
	
	
	/**
	 * 字符串src中添加
	 * @param src
	 * @param append
	 * @return
	 */
	public static String strArrJoinerArr(String src, String... append) {
		
		if (src == null) {
			throw new IllegalArgumentException("源字符串为空");
		}
		
		// 切割后的参数
		List<String> list = Splitter.on(",").trimResults().omitEmptyStrings().splitToList(src);
		
		List<String> newList = new ArrayList<String>(list);
		
		for(int i = 0; i < append.length; i++) {
			newList.add(append[i]);
		}
		
		Joiner joiner = Joiner.on(',').skipNulls();
		
		String result = joiner.join(newList);
		
		return result;
	}
	
	
	/**
	 * 切割
	 * @param src
	 * @return
	 */
	public static String[] split(String src) {
		
		if (StringUtils.isEmpty(src)) {
			return new String[0];
		}
		
		List<String> list = Splitter.on(",").trimResults().omitEmptyStrings().splitToList(src);
		
		return list.toArray(new String[list.size()]);
	}
	

}
