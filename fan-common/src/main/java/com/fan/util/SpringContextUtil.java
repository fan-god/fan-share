package com.fan.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: shil20
 * @date: 2018/8/14 14:25
 **/
@Component
public class SpringContextUtil implements ApplicationContextAware {

    private static ApplicationContext ac; // Spring应用上下文环境

    /*
    * 实现了ApplicationContextAware 接口，必须实现该方法；
    *通过传递applicationContext参数初始化成员变量applicationContext
    */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtil.ac = applicationContext;
    }


    public static ApplicationContext getApplicationContext() {
        return ac;
    }

    public static <T> T getBean(String name) throws BeansException {
        return (T) ac.getBean(name);
    }

    public static <T> T getBean(Class<T> c) throws BeansException {
        return (T) ac.getBean(c);
    }
}
