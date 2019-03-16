package com.lgwork.tool;

import redis.embedded.RedisServer;


/**
 * 启动redis
 * @author irays
 *
 */
public class RedisServerStart {
	
	public static void main(String[] args) {
		
		RedisServer redisServer = RedisServer.builder()
				   .port(6379)
				   .setting("daemonize no")
				   .setting("appendonly no")
				   .setting("maxheap 128M")
				   .setting("requirepass root")
				   .build();
		
		redisServer.start();
		
	}
	

}
