//package com.fan.service;
//
//
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//
//public class RedisDataSourceImpl implements IRedisDataSource {
//
//	private Logger log = Logger.getLogger(getClass());
//
//	@Autowired
//	private ShardedJedisPool shardedJedisPool;
//
//	public ShardedJedis getRedisClient() {
//		try {
//			ShardedJedis shardJedis = shardedJedisPool.getResource();
//			return shardJedis;
//		} catch (Exception e) {
//			log.error("getRedisClent error", e);
//		}
//		return null;
//	}
//
//	public void returnResource(ShardedJedis shardedJedis) {
//		shardedJedisPool.returnResource(shardedJedis);
//	}
//
//	public void returnResource(ShardedJedis shardedJedis, boolean broken) {
//		if (broken) {
//			shardedJedisPool.returnBrokenResource(shardedJedis);
//		} else {
//			shardedJedisPool.returnResource(shardedJedis);
//		}
//	}
//}
