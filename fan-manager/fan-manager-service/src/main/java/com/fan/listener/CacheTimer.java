package com.fan.listener;

import java.util.TimerTask;

import com.fan.entity.BaseFile;
import com.fan.entity.User;
import com.fan.interfaces.IBaseFileService;
import com.fan.interfaces.IUserService;
import com.fan.util.RedisUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CacheTimer extends TimerTask {

	ApplicationContext context = null;
	{
		context = new ClassPathXmlApplicationContext("classpath:spring-mybatis.xml");
	}

	public void run() {
//		IBaseFileService bfs = (IBaseFileService) context.getBean("baseFileServiceImpl");
//		for (BaseFile bf : bfs.getAllFile()) {
//			RedisUtil.intoSingleCache(1, "FILE", bf);
//		}
//		RedisUtil.intoCompleteCache(2, bfs.getAllFile(), "FILE");
//
//		IUserService us = (IUserService) context.getBean("userServiceImpl");
//		for (User user : us.getAllUser()) {
//			RedisUtil.intoSingleCache(1, "USER", user);
//		}
//		RedisUtil.intoCompleteCache(2, us.getAllUser(), "USER");
//		System.out.println("-------------------缓存加载完毕-----------------");
	}
}
