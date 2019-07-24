package com.fan.timetask;

import com.fan.entity.User;
import com.fan.service.IUserService;
import com.fan.util.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;

@Slf4j
@Component
public class ServerJob {
    @Autowired
    private RedisUtil redisUtil;

    public void method() {
        try {
            IUserService iUserService = SpringContextUtil.getBean(IUserService.class);
            User user = new User();
            user.setUsername(StrUtil.getRandomStr(10));
            user.setPassword(StrUtil.getRandomStr(10));
            user.setPhone(String.valueOf(1) + RandomUtils.nextLong(1000000000, 9999999999L));
            int sexRandom = new Random().nextInt(2);
            if (sexRandom > 0) {
                user.setSex(sexRandom == 1 ? "男" : "女");
            }
            user.setStat(1);
            int count = iUserService.insert(user);
            if (count > 0) {
                redisUtil.addValue("USER", DataConvertUtil.beanToString(user, ConstantFan.JSON), 1);
                System.out.println("插入一条,并加载缓存数据成功！");
            }
        } catch (Exception e) {
            log.error("ServerJob error:{}", e);
        }
    }

    public void method2() {
    }
}
