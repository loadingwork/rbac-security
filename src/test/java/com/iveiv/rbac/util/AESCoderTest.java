package com.iveiv.rbac.util;

import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

/**
 * 
 * @author irays
 *
 */
public class AESCoderTest {
	
	

	@Test
	public void testInitKeyInt() throws Exception {
		
		
		byte[] initKey = AESCoder.initKey(256);
		
		String encodeBase64String = Base64.encodeBase64String(initKey);
		
		System.err.println(encodeBase64String);
		
		
	}

}
