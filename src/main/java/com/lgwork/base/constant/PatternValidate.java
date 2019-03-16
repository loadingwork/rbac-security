package com.lgwork.base.constant;

import java.util.regex.Pattern;

/**
 * 正则表达式
 * @author irays
 *
 */
public class PatternValidate {
	
	/**
	 * 邮箱 
	 */
	public static final  Pattern  EMAIL = Pattern.compile("^[a-zA-Z0-9.!#$%&'*+\\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$");
	/**
	 * url
	 */
	public static final Pattern URL = Pattern.compile("^(?:(?:(?:https?|ftp):)?\\/\\/)(?:\\S+(?::\\S*)?@)?(?:(?!(?:10|127)(?:\\.\\d{1,3}){3})(?!(?:169\\.254|192\\.168)(?:\\.\\d{1,3}){2})(?!172\\.(?:1[6-9]|2\\d|3[0-1])(?:\\.\\d{1,3}){2})(?:[1-9]\\d?|1\\d\\d|2[01]\\d|22[0-3])(?:\\.(?:1?\\d{1,2}|2[0-4]\\d|25[0-5])){2}(?:\\.(?:[1-9]\\d?|1\\d\\d|2[0-4]\\d|25[0-4]))|(?:(?:[a-z\\u00a1-\\uffff0-9]-*)*[a-z\\u00a1-\\uffff0-9]+)(?:\\.(?:[a-z\\u00a1-\\uffff0-9]-*)*[a-z\\u00a1-\\uffff0-9]+)*(?:\\.(?:[a-z\\u00a1-\\uffff]{2,})).?)(?::\\d{2,5})?(?:[/?#]\\S*)?$");
	/**
	 * 数字  org.apache.commons.lang3.Validate也是可以的
	 */
	public static final Pattern NUMBER = Pattern.compile("^(?:-?\\d+|-?\\d{1,3}(?:,\\d{3})+)?(?:\\.\\d+)?$");
	/**
	 * 数字
	 */
	public static final Pattern DIGITS = Pattern.compile("^\\d+$");
	
	/**
	 * 权限编码
	 */
	public static final Pattern SYS_OPERATION_CODE = Pattern.compile("^[a-z]{1}[a-z1-9_]{1,255}$");
	

}
