package com.fan.listener;

import com.fan.service.impl.UserRegisterEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author fan
 * @title: RegisterUserApplicationListener
 * @projectName fan-share
 * @description: TODO
 * @date 2019/8/27/002715:13
 */
@Component
public class RegisterUserApplicationListener implements ApplicationListener<UserRegisterEvent> {
    @Override
    public void onApplicationEvent(UserRegisterEvent userRegisterEvent) {
        System.out.println("实现接口ApplicationListener监听用户注册事件并向用户发送邮件");
        System.out.println("注册用户名：" + userRegisterEvent.getUser().getUsername());
    }
}
