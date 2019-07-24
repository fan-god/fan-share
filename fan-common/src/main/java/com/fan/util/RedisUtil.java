	
package com.fan.util;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: RedisUtils
 * @Description: 该类用于reids的工具类，
 * @author fan
 * @date 2018年7月19日 redisTemplate.opsForHash();//操作hash
 *       redisTemplate.opsForValue();//操作字符串 redisTemplate.opsForSet();//操作set
 *       redisTemplate.opsForList();//操作list
 *       redisTemplate.opsForZSet();//操作有序set
 *
 */
@Slf4j
@Component("redisUtil")
public class RedisUtil {

	private static final Logger logger = LoggerFactory.getLogger(RedisUtil.class);

	private static final String UNLOCK_LUA = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end ";

	/**
	 * 将key 的值设为value ，当且仅当key 不存在，等效于 SETNX。
	 */
	public static final String NX = "NX";

	/**
	 * seconds — 以秒为单位设置 key 的过期时间，等效于EXPIRE key seconds
	 */
	public static final String EX = "EX";

	/**
	 * 调用set后的返回值
	 */
	public static final String OK = "OK";

    @Resource(name = "redisTemplate")
	private RedisTemplate<String,Object> redisTemplate;

	@Resource(name = "redisConnectionFactory")
	private JedisConnectionFactory redisConnectionFactory;

	@Autowired(required = false)
	public void setRedisTemplate(RedisTemplate redisTemplate) {
		RedisSerializer stringSerializer = new StringRedisSerializer();
		redisTemplate.setKeySerializer(stringSerializer);
		redisTemplate.setValueSerializer(stringSerializer);
		redisTemplate.setHashKeySerializer(stringSerializer);
		redisTemplate.setHashValueSerializer(stringSerializer);
		this.redisTemplate = redisTemplate;
	}

	private RedisTemplate<String, Object> getRedisTemplate(Integer dbIndex) {
		redisConnectionFactory.setDatabase(dbIndex);
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }
	/**
	 * @Title: emptyRedis
	 * @Description: 该方法用于清空库缓存
	 * @param @return 参数
	 * @return boolean 返回类型
	 * @throws
	 */
	public boolean emptyRedis(Integer dbIndex) {
		try {
			getRedisTemplate(dbIndex).execute(new RedisCallback<Object>() {
				public String doInRedis(RedisConnection connection)
						throws DataAccessException {
					connection.flushDb();
					return "OK";
				}
			});
			return true;
		} catch (Exception e) {
			logger.error("RedisUtil.emptyRedis()  error:{} " + e);
			return false;
		}
	}

	/**
	 * @Title: switchRedisLibrary
	 * @Description: 该方法用于切换redis的库所在库
	 * @param @return 参数
	 * @return boolean 返回类型
	 * @throws
	 */
	public boolean switchRedisLibrary(Integer redisIndex) {
		try {
			 /*进行切换，若redis等宕机，捕获抛出异常,切换成功则返回true，失败则为false*/ 
			JedisConnectionFactory jedisConnectionFactory = (JedisConnectionFactory) redisTemplate
					.getConnectionFactory();
			jedisConnectionFactory.setDatabase(redisIndex);
			redisTemplate.setConnectionFactory(jedisConnectionFactory);
			return true;
		} catch (Exception e) {
			logger.error("RedisUtil.switchRedisLibrary()  error:{} " + e);
			return false;
		}
	}
	
	/**
	 * 指定缓存失效时间
	 * @param key 键
	 * @param time 时间(秒)
	 * @return
	 */
	public boolean expire(String key,long time,Integer dbIndex){
		try {
			if(time>0){
				getRedisTemplate(dbIndex).expire(key, time, TimeUnit.SECONDS);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("RedisUtil.expire()  error:{} " + e);
			return false;
		}
	}
	
	/**
	 * 根据key 获取过期时间
	 * @param key 键 不能为null
	 * @return 时间(秒) 返回0代表为永久有效
	 */
	public long getExpire(String key,Integer dbIndex){
		return getRedisTemplate(dbIndex).getExpire(key,TimeUnit.SECONDS);
	}
	
	/**
	 * 判断key是否存在
	 * @param key 键
	 * @return true 存在 false不存在
	 */
	public boolean hasKey(String key,Integer dbIndex){
		try {
			return getRedisTemplate(dbIndex).hasKey(key);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("RedisUtil.hasKey()  error:{} " + e);
			return false;
		}
	}
	

	/*-----------------  String   ----------------------*/
	/**
	 * @Title: add
	 * @Description: 用于向reids中发送key和value值
	 * @param @param key
	 * @param @param value
	 * @param @return 参数
	 * @return boolean 返回类型
	 * @throws
	 */
	public boolean addValue(String key, String value,Integer dbIndex) {
		try {
			if (getRedisTemplate(dbIndex) == null) {
				return false;
			} else {
				getRedisTemplate(dbIndex).opsForValue().set(key, value);
			}
			return true;
		} catch (Exception e) {
			logger.error("RedisUtil.addValue()  error:{} " + e);
			return false;
		}
	}
	
	
    /**
    * @Title: addMap
    * @Description: 批量添加数据
    * @param @param maps
    * @param @param dbIndex
    * @param @return    参数
    * @return boolean    返回类型
    * @throws
    */
    public boolean addMap(final Map<String,String> maps, final Integer dbIndex) {
        boolean result = getRedisTemplate(dbIndex).execute(new RedisCallback<Boolean>() {
            public Boolean doInRedis(RedisConnection connection)
                    throws DataAccessException {
                RedisSerializer<String> serializer = getRedisTemplate(dbIndex).getStringSerializer();
                for (Map.Entry<String, String> entry : maps.entrySet()) {
 				   byte[] key = serializer.serialize(entry.getKey());
 				   byte[] value = serializer.serialize(entry.getValue());
 				   connection.setNX(key, value);
     			}
                return true;
            }
        }, false, true);
        return result;
    }
	
	
	
	/**
	 * 普通缓存放入并设置时间
	 * @param key 键
	 * @param value 值
	 * @param time 时间(秒) time要大于0 如果time小于等于0 将设置无限期
	 * @return true成功 false 失败
	 */
	public boolean addValueTime(String key,String value,long time,Integer dbIndex){
		try {
			if(time>0){
				getRedisTemplate(dbIndex).opsForValue().set(key, value, time, TimeUnit.SECONDS);
			}else{
				addValue(key, value,dbIndex);
			}
			return true;
		} catch (Exception e) {
			logger.error("RedisUtil.addValueTime()  error:{} " + e);
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * @Title: delete
	 * @Description: 通过key值删除
	 * @param @param key
	 * @param @return 参数
	 * @return boolean 返回类型
	 * @throws
	 */
	public boolean deleteValue(String key,Integer dbIndex) {
		try {
			if (getRedisTemplate(dbIndex) == null) {
				return false;
			}
			getRedisTemplate(dbIndex).delete(key);
			return true;
		} catch (Exception e) {
			logger.error("RedisUtil.deleteValue()  error:{} " + e);
			return false;
		}
	}

	/**
	 * @Title: update
	 * @Description: 通过Key修改value的参数
	 * @param @param key
	 * @param @param value
	 * @param @return 参数
	 * @return boolean 返回类型
	 * @throws
	 */
	public boolean updateValue(String key, String value,Integer dbIndex) {
		try {
			if (getRedisTemplate(dbIndex) == null) {
				return false;
			}
			getRedisTemplate(dbIndex).opsForValue().set(key, value);
			return true;
		} catch (Exception e) {
			logger.error("RedisUtil.updateValue()  error:{} " + e);
			return false;
		}
	}

	/**
	 * @Title: get
	 * @Description:
	 * @param @param key
	 * @param @return 参数
	 * @return String 返回类型
	 * @throws
	 */
	public String getValue(String key,Integer dbIndex) {
		try {
			if (getRedisTemplate(dbIndex) == null) {
				return null;
			}
			Object valueObj = getRedisTemplate(dbIndex).opsForValue().get(key);
			String value = null;
			if(valueObj != null){
				value = (String) getRedisTemplate(dbIndex).opsForValue().get(key);
			}
			return value;
		} catch (Exception e) {
			logger.error("RedisUtil.getValue()  error:{} " + e);
			return null;
		}
	}

	/**
	 * @Title: get
	 * @Description:
	 * @param @param key
	 * @param @return 参数
	 * @return String 返回类型
	 * @throws
	 */
	public Boolean getBooleanValue(String key,Integer dbIndex,Boolean defValue) {
		try {
			if (getRedisTemplate(dbIndex) == null) {
				return null;
			}
			Object valueObj = getRedisTemplate(dbIndex).opsForValue().get(key);
			String value = null;
			if(valueObj != null){
				value = (String) getRedisTemplate(dbIndex).opsForValue().get(key);
			}
			if(org.apache.commons.lang3.StringUtils.isBlank(value)){
				return defValue;
			}else{
				return BooleanUtils.toBoolean(value);
			}
		} catch (Exception e) {
			logger.error("RedisUtil.getValue()  error:{} " + e);
			return defValue;
		}
	}

	/**
	 * @Title: get
	 * @Description:
	 * @param key
	 * @param dbIndex 数据库索引下标
	 * @param defaultValue 默认值
	 * @return String 返回类型
	 * @throws
	 */
	public String getValue(String key,Integer dbIndex,String defaultValue) {
		try {
			if (getRedisTemplate(dbIndex) == null) {
				return null;
			}
			Object valueObj = getRedisTemplate(dbIndex).opsForValue().get(key);
			String value = null;
			if(valueObj != null){
				value = (String) getRedisTemplate(dbIndex).opsForValue().get(key);
			}else{
				value = defaultValue;
			}
			return value;
		} catch (Exception e) {
			logger.error("RedisUtil.getValue()  error:{} " + e);
			return null;
		}
	}

	/**
	 * @Title: checkKey
	 * @Description: String 类型的check校验，判断是否存在value
	 * @param @param key
	 * @param @return 参数
	 * @return boolean 返回类型
	 * @throws
	 */
	public boolean checkValue(String key,Integer dbIndex) {
		try {
			if (getRedisTemplate(dbIndex) == null) {
				return false;
			} else {
				Object object = getRedisTemplate(dbIndex).opsForValue().get(key);
				if (null == object) {
					return false;
				}
				return true;
			}
		} catch (Exception e) {
			logger.error("RedisUtil.checkValue()  error:{} " + e);
			return false;
		}
	}

	/*-----------------  list   ----------------------*/

	/**
	 * 获取list缓存的具体下标对应的数据
	 * 
	 * @param key
	 *            键
	 * @param start
	 *            开始
	 * @param end
	 *            结束 0 到 -1代表所有值
	 * @return
	 */
	public List<Object> getListStartEnd(String key, long start, long end,Integer dbIndex) {
		try {
			return getRedisTemplate(dbIndex).opsForList().range(key, start, end);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("RedisUtil.getListStartEnd()  error:{} " + e);
			return null;
		}
	}

	/**
	 * 获取list缓存的长度
	 * 
	 * @param key
	 *            键
	 * @return
	 */
	public long getListSize(String key,Integer dbIndex) {
		try {
			return getRedisTemplate(dbIndex).opsForList().size(key);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("RedisUtil.getListSize()  error:{} " + e);
			return 0;
		}
	}

	/**
	 * 通过索引 获取list中的值
	 * 
	 * @param key
	 *            键
	 * @param index
	 *            索引 index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
	 * @return
	 */
	public Object getListIndex(String key, long index,Integer dbIndex) {
		try {
			return getRedisTemplate(dbIndex).opsForList().index(key, index);
		} catch (Exception e) {
			logger.error("RedisUtil.getListIndex()  error:{} " + e);
			e.printStackTrace();
			return null;
		}
	}

	
	/** 
     * 将list放入缓存 
     * @param key 键 
     * @param value 值 
     * @return
     */  
    public boolean setList(String key, List<Object> value,Integer dbIndex) {  
        try {  
        	getRedisTemplate(dbIndex).opsForList().rightPushAll(key, value);  
            return true;  
        } catch (Exception e) {  
        	logger.error("RedisUtil.setList()  error:{} " + e);
            return false;  
        }  
    }  
      
    /** 
     * 将list放入缓存 ，并设置过期时间
     * @param key 键 
     * @param value 值 
     * @param time 时间(秒) 
     * @return 
     */  
    public boolean setListTime(String key, List<Object> value, long time,Integer dbIndex) {  
        try {  
        	getRedisTemplate(dbIndex).opsForList().rightPushAll(key, value);  
            if (time > 0) expire(key, time, dbIndex);  
            return true;  
        } catch (Exception e) {  
        	logger.error("RedisUtil.setListTime()  error:{} " + e);
            return false;  
        }  
    }  
	/**
	 * 将list放入缓存
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @return
	 */
	public boolean setListObject(String key, Object value,Integer dbIndex) {
		try {
			getRedisTemplate(dbIndex).opsForList().rightPush(key, value);
			return true;
		} catch (Exception e) {
			logger.error("RedisUtil.setListObject()  error:{} " + e);
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 将list放入缓存
	 * @param key 键
	 * @param value 值
	 * @param time 时间(秒)
	 * @return
	 */
	public boolean setListObjectTime(String key, Object value, long time,Integer dbIndex) {
        try {
        	getRedisTemplate(dbIndex).opsForList().rightPush(key, value);
            if (time > 0) expire(key, time, dbIndex);
            return true;
        } catch (Exception e) {
        	logger.error("RedisUtil.setListObject()  error:{} " + e);
        	return false;
        }
	}

	/**
	 * 根据索引修改list中的某条数据
	 * 
	 * @param key
	 *            键
	 * @param index
	 *            索引
	 * @param value
	 *            值
	 * @return
	 */
	public boolean updateListIndex(String key, long index, Object value,Integer dbIndex) {
		try {
			getRedisTemplate(dbIndex).opsForList().set(key, index, value);
			return true;
		} catch (Exception e) {
			logger.error("RedisUtil.updateListIndex()  error:{} " + e);
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 移除N个值为value
	 * 
	 * @param key
	 *            键
	 * @param count
	 *            移除多少个
	 * @param value
	 *            值
	 * @return 移除的个数
	 */
	public long removeListCount(String key, long count, Object value,Integer dbIndex) {
		try {
			Long remove = getRedisTemplate(dbIndex).opsForList().remove(key, count, value);
			return remove;
		} catch (Exception e) {
			logger.error("RedisUtil.removeListCount()  error:{} " + e);
			e.printStackTrace();
			return 0;
		}
	}

	/*-----------------  hash   ----------------------*/
	/**
	 * @Title: addObj
	 * @Description: opsForHash 这是操作hash，该方法主要是设置散列的hashKey的值
	 * @param @param objectKey
	 * @param @param key
	 * @param @param object
	 * @param @return 参数
	 * @return boolean 返回类型
	 * @throws
	 */
	public boolean addObj(String objectKey, String key, Object object,Integer dbIndex) {
		if (getRedisTemplate(dbIndex) == null) {
			return false;
		} else {
			getRedisTemplate(dbIndex).opsForHash().put(objectKey, key, object);
		}
		return true;
	}

	/*-----------------  set   ----------------------*/
	/**
	 * 根据key获取Set中的所有值
	 * 
	 * @param key
	 *            键
	 * @return
	 */
	public Set<Object> getSetValue(String key,Integer dbIndex) {
		try {
			return getRedisTemplate(dbIndex).opsForSet().members(key);
		} catch (Exception e) {
			logger.error("RedisUtil.getSetValue()  error:{} " + e);
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 根据value从一个set中查询,是否存在
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * @return true 存在 false不存在
	 */
	public boolean sHasKey(String key, Object value,Integer dbIndex) {
		try {
			return getRedisTemplate(dbIndex).opsForSet().isMember(key, value);
		} catch (Exception e) {
			logger.error("RedisUtil.sHasKey()  error:{} " + e);
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 将数据放入set缓存
	 * 
	 * @param key
	 *            键
	 * @param values
	 *            值 可以是多个
	 * @return 成功个数
	 */
	public long insertSet(String key,Integer dbIndex, Object... values) {
		try {
			return getRedisTemplate(dbIndex).opsForSet().add(key, values);
		} catch (Exception e) {
			logger.error("RedisUtil.insertSet()  error:{} " + e);
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 获取set缓存的长度
	 * 
	 * @param key
	 *            键
	 * @return
	 */
	public long sGetSetSize(String key,Integer dbIndex) {
		try {
			return getRedisTemplate(dbIndex).opsForSet().size(key);
		} catch (Exception e) {
			logger.error("RedisUtil.sGetSetSize()  error:{} " + e);
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 移除值为value的
	 * 
	 * @param key
	 *            键
	 * @param values
	 *            值 可以是多个
	 * @return 移除的个数
	 */
	public long setRemove(String key,Integer dbIndex, Object... values) {
		try {
			Long count = getRedisTemplate(dbIndex).opsForSet().remove(key, values);
			return count;
		} catch (Exception e) {
			logger.error("RedisUtil.setRemove()  error:{} " + e);
			e.printStackTrace();
			return 0;
		}
	}


	/**
	 * 尝试获取锁 超时返回
	 * @param lockKey
	 * @param lockValue
	 * @param expireTime 锁有效时间（秒）
	 * @param timeOutms 方法超时时间（毫秒）
	 * @return
	 */
	public boolean tryLock(String lockKey,String lockValue,long expireTime,long timeOutms) {

		long currentTimeMillis = System.currentTimeMillis();
		while ((System.currentTimeMillis() - currentTimeMillis) < timeOutms) {
			if (OK.equalsIgnoreCase(this.set(lockKey, lockValue, expireTime))) {
				String dateStr = DateFormatUtils.format(System.currentTimeMillis(),ConstantFan.DATE_PATTERN_17);
				String uuid = dateStr+"_"+UUID.randomUUID().toString();
				String value = dateStr+"_"+lockKey;
				redisTemplate.opsForValue().set(uuid,value,12*60*60, TimeUnit.SECONDS);
				return true;
			}
			if(Thread.currentThread().isInterrupted()){
				break;
			}
			// 每次请求等待一段时间
			seleep(500, 1000);
		}
		return false;
	}

	/**
	 * @param millis 毫秒
	 * @param nanos  纳秒
	 * @Title: seleep
	 * @Description: 线程等待时间
	 * @author yuhao.wang
	 */
	private void seleep(int millis, int nanos) {
		try {
			TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextInt(millis, nanos));
		} catch (InterruptedException e) {
			logger.info("获取分布式锁休眠被中断：{}", e);
		}
	}

	/**
	 * 重写redisTemplate的set方法
	 * <p>
	 * 命令 SET resource-name anystring NX EX max-lock-time 是一种在 Redis 中实现锁的简单方法。
	 * <p>
	 * 客户端执行以上的命令：
	 * <p>
	 * 如果服务器返回 OK ，那么这个客户端获得锁。
	 * 如果服务器返回 NIL ，那么客户端获取锁失败，可以在稍后再重试。
	 *
	 * @param key     锁的Key
	 * @param value   锁里面的值
	 * @param seconds 过去时间（秒）
	 * @return
	 */
	private String set(final String key, final String value, final long seconds) {
		Assert.isTrue(!StringUtils.isEmpty(key), "key不能为空");
		return redisTemplate.execute(new RedisCallback<String>() {
			@Override
			public String doInRedis(RedisConnection connection) throws DataAccessException {
				Object nativeConnection = connection.getNativeConnection();
				logger.debug("nativeConnection:{}",nativeConnection);
				String result = null;
				// 集群模式
				if (nativeConnection instanceof JedisCluster) {
					result = ((JedisCluster) nativeConnection).set(key, value, NX, EX, seconds);
				}
				// 单机模式
				if (nativeConnection instanceof Jedis) {
					result = ((Jedis) nativeConnection).set(key, value, NX, EX, seconds);
				}

				if (!StringUtils.isEmpty(key) && !StringUtils.isEmpty(result)) {
					logger.debug("get lock [{}] time : {}", key, System.currentTimeMillis());
				}
				logger.debug("result:{}",result);
				return result;
			}
		});
	}

	/**
	 * 解锁
	 * <p>
	 * 可以通过以下修改，让这个锁实现更健壮：
	 * <p>
	 * 不使用固定的字符串作为键的值，而是设置一个不可猜测（non-guessable）的长随机字符串，作为口令串（token）。
	 * 不使用 DEL 命令来释放锁，而是发送一个 Lua 脚本，这个脚本只在客户端传入的值和键的口令串相匹配时，才对键进行删除。
	 * 这两个改动可以防止持有过期锁的客户端误删现有锁的情况出现。
	 */
	public Boolean unlock(final String lockKey,final String lockValue) {
		List<String> keys = Lists.newArrayList();
		keys.add(lockKey);
		List<String> values = Lists.newArrayList();
		values.add(lockValue);
		Object obj = redisTemplate.execute(new DefaultRedisScript<>(UNLOCK_LUA, String.class),keys,values.toArray());
		Long result = (Long)obj;
		if (result == 0 && !StringUtils.isEmpty(lockKey)) {
			Object object = redisTemplate.opsForValue().get(lockKey);
			logger.debug("Redis distributed lock,result:{},lockValue:{},resultlockValue:{}"+object, result,lockValue);
			if(object != null){
				if(org.apache.commons.lang3.StringUtils.equals(object.toString(),lockValue)){
                    redisTemplate.delete(lockKey);
					return true;
				}
			}else{
				return true;
			}
		}
		return result == 1;
	}

}