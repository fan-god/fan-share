package com.fan.timetask;

import com.fan.entity.User;
import com.fan.interfaces.IUserService;
import com.fan.util.StrUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Random;

public class ServerJob {
	private Logger log = Logger.getLogger(getClass());
	@Autowired
	private IUserService iUserService;
	public void method() {
		try {
			User user = new User();
			user.setUsername(StrUtil.getRandomStr(10));
			user.setPassword(StrUtil.getRandomStr(10));
			int sexRandom = new Random().nextInt(2);
			if (sexRandom > 0) {
				user.setSex(sexRandom == 1 ? "男" : "女");
			}
			user.setStat(1);
			int count = iUserService.insert(user);
			if (count > 0) {
//				RedisUtil.intoSingleCache(1, "USER", user);
				System.out.println("插入一条,并加载缓存数据成功！");
			}
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
	}

	public void method2() {}
}
