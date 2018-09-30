package com.fan.util;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;


/**
* @ClassName: RedisConfig
* @Description: 配置多个reis连接
* @author boway
* @date 2018年8月9日
*
*/
@Configuration
public class RedisConfig {

	@Value("${redis.host}")
	private String hostName;  
//	private String hostName = "10.39.32.177";  
	
	@Value("${redis.port}")
	private int port;  
	
	@Value("${redis.password}")
	private String passWord;  
	
	@Value("${redis.maxIdle}")
	private int maxIdl;  
	
	@Value("${redis.minIdle}")
	private int minIdl;  
	
	@Value("${spring.redis.database0}")
	private int database0;  
	@Value("${spring.redis.database1}")
	private int database1;  
	@Value("${spring.redis.database2}")
	private int database2;  
	@Value("${spring.redis.database3}")
	private int database3;  
	@Value("${spring.redis.database4}")
	private int database4;  
	@Value("${spring.redis.database5}")
	private int database5;  
	@Value("${spring.redis.database6}")
	private int database6;  
  
    @Bean
    public RedisConnectionFactory redisConnectionFactory0() throws Exception{  
        JedisPoolConfig poolConfig=new JedisPoolConfig();  
        poolConfig.setMaxIdle(maxIdl);  
        poolConfig.setMinIdle(minIdl);  
        poolConfig.setTestOnBorrow(true);  
        poolConfig.setTestOnReturn(true);  
        poolConfig.setTestWhileIdle(true);  
        poolConfig.setNumTestsPerEvictionRun(10);  
        poolConfig.setTimeBetweenEvictionRunsMillis(60000);  
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(poolConfig);  
        jedisConnectionFactory.setHostName(hostName);  
        if(!passWord.isEmpty()){
            jedisConnectionFactory.setPassword(passWord);  
        }  
        jedisConnectionFactory.setPort(port);  
        jedisConnectionFactory.setDatabase(database0);  
        return jedisConnectionFactory;  
    }
    @Bean
    public RedisConnectionFactory redisConnectionFactory1() throws Exception{  
    	JedisPoolConfig poolConfig=new JedisPoolConfig();  
    	poolConfig.setMaxIdle(maxIdl);  
    	poolConfig.setMinIdle(minIdl);  
    	poolConfig.setTestOnBorrow(true);  
    	poolConfig.setTestOnReturn(true);  
    	poolConfig.setTestWhileIdle(true);  
    	poolConfig.setNumTestsPerEvictionRun(10);  
    	poolConfig.setTimeBetweenEvictionRunsMillis(60000);  
    	JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(poolConfig);  
    	jedisConnectionFactory.setHostName(hostName);  
    	if(!passWord.isEmpty()){
    		jedisConnectionFactory.setPassword(passWord);  
    	}  
    	jedisConnectionFactory.setPort(port);  
    	jedisConnectionFactory.setDatabase(database1);  
    	return jedisConnectionFactory;  
    }
    @Bean
    public RedisConnectionFactory redisConnectionFactory2() throws Exception{  
    	JedisPoolConfig poolConfig=new JedisPoolConfig();  
    	poolConfig.setMaxIdle(maxIdl);  
    	poolConfig.setMinIdle(minIdl);  
    	poolConfig.setTestOnBorrow(true);  
    	poolConfig.setTestOnReturn(true);  
    	poolConfig.setTestWhileIdle(true);  
    	poolConfig.setNumTestsPerEvictionRun(10);  
    	poolConfig.setTimeBetweenEvictionRunsMillis(60000);  
    	JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(poolConfig);  
    	jedisConnectionFactory.setHostName(hostName);  
    	if(!passWord.isEmpty()){
    		jedisConnectionFactory.setPassword(passWord);  
    	}  
    	jedisConnectionFactory.setPort(port);  
    	jedisConnectionFactory.setDatabase(database2);  
    	return jedisConnectionFactory;  
    }
    @Bean
    public RedisConnectionFactory redisConnectionFactory3() throws Exception{  
    	JedisPoolConfig poolConfig=new JedisPoolConfig();  
    	poolConfig.setMaxIdle(maxIdl);  
    	poolConfig.setMinIdle(minIdl);  
    	poolConfig.setTestOnBorrow(true);  
    	poolConfig.setTestOnReturn(true);  
    	poolConfig.setTestWhileIdle(true);  
    	poolConfig.setNumTestsPerEvictionRun(10);  
    	poolConfig.setTimeBetweenEvictionRunsMillis(60000);  
    	JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(poolConfig);  
    	jedisConnectionFactory.setHostName(hostName);  
    	if(!passWord.isEmpty()){
    		jedisConnectionFactory.setPassword(passWord);  
    	}  
    	jedisConnectionFactory.setPort(port);  
    	jedisConnectionFactory.setDatabase(database3);  
    	return jedisConnectionFactory;  
    }
    @Bean
    public RedisConnectionFactory redisConnectionFactory4() throws Exception{  
    	JedisPoolConfig poolConfig=new JedisPoolConfig();  
    	poolConfig.setMaxIdle(maxIdl);  
    	poolConfig.setMinIdle(minIdl);  
    	poolConfig.setTestOnBorrow(true);  
    	poolConfig.setTestOnReturn(true);  
    	poolConfig.setTestWhileIdle(true);  
    	poolConfig.setNumTestsPerEvictionRun(10);  
    	poolConfig.setTimeBetweenEvictionRunsMillis(60000);  
    	JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(poolConfig);  
    	jedisConnectionFactory.setHostName(hostName);  
    	if(!passWord.isEmpty()){
    		jedisConnectionFactory.setPassword(passWord);  
    	}  
    	jedisConnectionFactory.setPort(port);  
    	jedisConnectionFactory.setDatabase(database4);  
    	return jedisConnectionFactory;  
    }
    @Bean
    public RedisConnectionFactory redisConnectionFactory5() throws Exception{  
    	JedisPoolConfig poolConfig=new JedisPoolConfig();  
    	poolConfig.setMaxIdle(maxIdl);  
    	poolConfig.setMinIdle(minIdl);  
    	poolConfig.setTestOnBorrow(true);  
    	poolConfig.setTestOnReturn(true);  
    	poolConfig.setTestWhileIdle(true);  
    	poolConfig.setNumTestsPerEvictionRun(10);  
    	poolConfig.setTimeBetweenEvictionRunsMillis(60000);  
    	JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(poolConfig);  
    	jedisConnectionFactory.setHostName(hostName);  
    	if(!passWord.isEmpty()){
    		jedisConnectionFactory.setPassword(passWord);  
    	}  
    	jedisConnectionFactory.setPort(port);  
    	jedisConnectionFactory.setDatabase(database5);  
    	return jedisConnectionFactory;  
    }
    @Bean
    public RedisConnectionFactory redisConnectionFactory6() throws Exception{  
    	JedisPoolConfig poolConfig=new JedisPoolConfig();  
    	poolConfig.setMaxIdle(maxIdl);  
    	poolConfig.setMinIdle(minIdl);  
    	poolConfig.setTestOnBorrow(true);  
    	poolConfig.setTestOnReturn(true);  
    	poolConfig.setTestWhileIdle(true);  
    	poolConfig.setNumTestsPerEvictionRun(10);  
    	poolConfig.setTimeBetweenEvictionRunsMillis(60000);  
    	JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(poolConfig);  
    	jedisConnectionFactory.setHostName(hostName);  
    	if(!passWord.isEmpty()){
    		jedisConnectionFactory.setPassword(passWord);  
    	}  
    	jedisConnectionFactory.setPort(port);  
    	jedisConnectionFactory.setDatabase(database6);  
    	return jedisConnectionFactory;  
    }
  
    @Bean(name = "redisTemplate")
    public RedisTemplate<String, Object> redisTemplateObject() throws Exception {  
        RedisTemplate<String, Object> redisTemplateObject = new RedisTemplate<String, Object>();  
        redisTemplateObject.setConnectionFactory(redisConnectionFactory0());  
        setSerializer(redisTemplateObject);  
        redisTemplateObject.afterPropertiesSet();  
        return redisTemplateObject;  
    }  
    @Bean(name = "redisTemplate0")
    public RedisTemplate<String, Object> redisTemplateObject0() throws Exception {  
    	RedisTemplate<String, Object> redisTemplateObject = new RedisTemplate<String, Object>();  
    	redisTemplateObject.setConnectionFactory(redisConnectionFactory0());  
    	setSerializer(redisTemplateObject);  
    	redisTemplateObject.afterPropertiesSet();  
    	return redisTemplateObject;  
    }  
    @Bean(name = "redisTemplate1")
    public RedisTemplate<String, Object> redisTemplateObject1() throws Exception {  
    	RedisTemplate<String, Object> redisTemplateObject = new RedisTemplate<String, Object>();  
    	redisTemplateObject.setConnectionFactory(redisConnectionFactory1());  
    	setSerializer(redisTemplateObject);  
    	redisTemplateObject.afterPropertiesSet();  
    	return redisTemplateObject;  
    }  
    @Bean(name = "redisTemplate2")
    public RedisTemplate<String, Object> redisTemplateObject2() throws Exception {  
    	RedisTemplate<String, Object> redisTemplateObject = new RedisTemplate<String, Object>();  
    	redisTemplateObject.setConnectionFactory(redisConnectionFactory2());  
    	setSerializer(redisTemplateObject);  
    	redisTemplateObject.afterPropertiesSet();  
    	return redisTemplateObject;  
    }  
    @Bean(name = "redisTemplate3")
    public RedisTemplate<String, Object> redisTemplateObject3() throws Exception {  
    	RedisTemplate<String, Object> redisTemplateObject = new RedisTemplate<String, Object>();  
    	redisTemplateObject.setConnectionFactory(redisConnectionFactory3());  
    	setSerializer(redisTemplateObject);  
    	redisTemplateObject.afterPropertiesSet();  
    	return redisTemplateObject;  
    }  
    @Bean(name = "redisTemplate4")
    public RedisTemplate<String, Object> redisTemplateObject4() throws Exception {  
    	RedisTemplate<String, Object> redisTemplateObject = new RedisTemplate<String, Object>();  
    	redisTemplateObject.setConnectionFactory(redisConnectionFactory4());  
    	setSerializer(redisTemplateObject);  
    	redisTemplateObject.afterPropertiesSet();  
    	return redisTemplateObject;  
    }  
    @Bean(name = "redisTemplate5")
    public RedisTemplate<String, Object> redisTemplateObject5() throws Exception {  
    	RedisTemplate<String, Object> redisTemplateObject = new RedisTemplate<String, Object>();  
    	redisTemplateObject.setConnectionFactory(redisConnectionFactory5());  
    	setSerializer(redisTemplateObject);  
    	redisTemplateObject.afterPropertiesSet();  
    	return redisTemplateObject;  
    }  
  
    private void setSerializer(RedisTemplate<String, Object> template) {  
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(  
                Object.class);  
        ObjectMapper om = new ObjectMapper();  
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);  
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);  
        jackson2JsonRedisSerializer.setObjectMapper(om);  
        template.setKeySerializer(template.getStringSerializer());  
        template.setValueSerializer(jackson2JsonRedisSerializer);  
        template.setHashValueSerializer(jackson2JsonRedisSerializer);  
        //在使用String的数据结构的时候使用这个来更改序列化方式  
		RedisSerializer<String> stringSerializer = new StringRedisSerializer(); 
		template.setKeySerializer(stringSerializer ); 
		template.setValueSerializer(stringSerializer ); 
		template.setHashKeySerializer(stringSerializer ); 
		template.setHashValueSerializer(stringSerializer );  
  
    }  
  
}