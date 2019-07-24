package com.fan.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
* @ClassName: DataSource
* @Description: 该类为自定义注解类，用于在dao层时切换数据源 
* @author fan
* @date 2018年9月14日
*/
/*@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)*/
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DataSource {
	String value();
}
