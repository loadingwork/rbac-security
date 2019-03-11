package com.iveiv.tool;

import redis.embedded.RedisServer;

/**
 * 停止redis
 * @author irays
 *
 */
public class RedisServerStop {

	public static void main(String[] args) {

		RedisServer redisServer = RedisServer.builder().port(6379).setting("daemonize no").setting("appendonly no")
				.setting("maxheap 128M").setting("requirepass root").build();

		redisServer.stop();

	}

}
