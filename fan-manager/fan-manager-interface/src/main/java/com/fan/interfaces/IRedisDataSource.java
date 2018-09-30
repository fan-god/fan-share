//package com.fan.interfaces;
//
//import redis.clients.jedis.ShardedJedis;
//
//public interface IRedisDataSource {
//	public abstract ShardedJedis getRedisClient(); // 获取redis客户端
//
//	public void returnResource(ShardedJedis shardedJedis);// 将资源返还给pool
//
//	public void returnResource(ShardedJedis shardedJedis, boolean broken);// 出现异常后，将资源返还给pool
//																			// （其实不需要第二个方法）
//}
