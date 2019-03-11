package com.iveiv.test;

import java.io.File;
import java.io.FileInputStream;

import org.apache.commons.codec.binary.Base64;

public class Demo11 {
	
	public static void main(String[] args) throws Exception {
		
		
		try {
			
			File file = new File("D:/tmp/java.png");
			
			FileInputStream fileInputStream = new FileInputStream(file);
			
			byte[] in = new byte[fileInputStream.available()];
			
			fileInputStream.read(in);
			System.out.println(in);
			fileInputStream.close();
			
			String encodeBase64String = Base64.encodeBase64String(in);
			
			String encodeToString = java.util.Base64.getEncoder().encodeToString(in);
			
			System.out.println(encodeToString);
			
			System.out.println(encodeBase64String);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
		
	}

}
