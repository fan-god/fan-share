package com.fan.util;

/**
 * Created by fan on 2018/9/5.
 * 这个类用于放国际化的配置文件每个key的名字常量
 */
public interface LangConstant {
    /************************国际化字段名称常量********************************/
    String LANGUAGE = "language";
    String BASENAME = "config.message";

    /********************国家语言配置常量**********************/
    enum Country {
        zh_CN,ja_JP,en_US,zh_HK

    }

    /**************************************************properties文件字段配置*********************************/
    /***********************公共配置**************************/
    enum Common {
        //分页没传页面显示页数
        error_pageSize,
        //参数非空
        no_empty,
        request_param;
    }

    enum MsgData{
        handling_failure,handling_success,login_out
    }
}
