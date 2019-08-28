package com.fan.listener;

import com.fan.service.impl.UserRegisterEvent;
import com.fan.service.impl.UserServiceImpl;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author fan
 * @title: RegisterUserSmartApplicationListener
 * @projectName fan-share
 * @description: 实现接口SmartApplicationListener实现监听用户注册
 * @date 2019/8/27/002715:00
 */
@Component
public class RegisterUserSmartApplicationListener implements SmartApplicationListener {
    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> aClass) {
        return aClass == UserRegisterEvent.class;
    }

    /**
     * 注意此处aClass不能与IUserService.class比较
     *
     * @param aClass
     * @return
     */
    @Override
    public boolean supportsSourceType(Class<?> aClass) {
        return aClass == UserServiceImpl.class;
    }

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        UserRegisterEvent userRegisterEvent = (UserRegisterEvent) applicationEvent;
        System.out.println("实现接口SmartApplicationListener监听用户注册事件并向用户发送邮件");
        System.out.println("注册用户名：" + userRegisterEvent.getUser().getUsername());
    }

    /**
     * 返回值越小监听越靠前
     *
     * @return
     */
    @Override
    public int getOrder() {
        return 1;
    }
}
