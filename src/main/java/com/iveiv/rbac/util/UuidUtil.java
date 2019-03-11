package com.iveiv.rbac.util;

import java.util.UUID;

/**
 * 
 * uuid工具类
 * 
 * @author irays
 *
 */
public class UuidUtil {
	
	public static String uuid32() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
