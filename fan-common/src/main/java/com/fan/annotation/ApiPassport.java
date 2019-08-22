package com.fan.annotation;

import java.lang.annotation.*;

/**
 * Created by Administrator on 2019/8/21/0021.
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Target(ElementType.METHOD)
public @interface ApiPassport {
    //true表示拦截，false表示通行
    boolean apiValidate() default true;
}
