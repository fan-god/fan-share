package com.fan.util;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

/**
 * @description:日期转化器
 * @author: fan
 * @date: 2018/7/25 14:59
 **/
public class MyWebBinding implements WebBindingInitializer {

    public void initBinder(WebDataBinder binder, WebRequest request) {
        binder.registerCustomEditor(Date.class, new DateConvertEditor());
    }

}
