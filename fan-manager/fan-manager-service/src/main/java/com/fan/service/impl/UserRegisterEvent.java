package com.fan.service.impl;

import com.fan.entity.User;
import org.springframework.context.ApplicationEvent;

/**
 * @author fan
 * @title: UserRegisterEvent
 * @projectName fan-share
 * @description: 事件定义类
 * @date 2019/8/27/002714:54
 */
public class UserRegisterEvent extends ApplicationEvent {
    private User user;

    /**
     * source 表示事件源对象
     * user表示注册用户对象
     *
     * @param source
     * @param user
     */
    public UserRegisterEvent(Object source, User user) {
        super(source);
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
