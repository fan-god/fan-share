package com.fan.listener;

import com.fan.service.impl.UserRegisterEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author fan
 * @title: AnnotationUserRegisterListener
 * @projectName fan-share
 * @description: 利用@EventListener注解监听用户注册事件
 * @date 2019/8/27/002714:57
 */
@Component
public class AnnotationUserRegisterListener {

    @EventListener
    public void sendMailToUser(UserRegisterEvent userRegisterEvent){
        System.out.println("利用@EventListener注解监听用户注册事件并向用户发送邮件");
        System.out.println("注册用户名：" + userRegisterEvent.getUser().getUsername());
    }
}
