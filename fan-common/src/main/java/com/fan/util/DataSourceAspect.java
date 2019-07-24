package com.fan.util;

import com.fan.annotation.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

@Slf4j
public class DataSourceAspect {

    //	拦截目标方法，获取由@DataSource指定的数据源标识，设置到线程存储中以便切换数据源
    public void intercept(JoinPoint point) throws Exception {
        Class<?> target = point.getTarget().getClass();
        MethodSignature signature = (MethodSignature) point.getSignature();
        // 默认使用目标类型的注解，如果没有则使用其实现接口的注解

        for (Class<?> clazz : target.getInterfaces()) {
            resolveDataSource(clazz, signature.getMethod());
        }
        resolveDataSource(target, signature.getMethod());
    }

    /**
     * 提取目标对象方法注解和类型注解中的数据源标识
     */

    public void resolveDataSource(Class<?> clazz, Method method) {
        try {
            Class<?>[] classes = method.getParameterTypes();
//			默认使用类型注解
            if (clazz.isAnnotationPresent(DataSource.class)) {
                DataSource source = clazz.getAnnotation(DataSource.class);
                String value = source.value();
                log.info("使用数据库:{}", value);
                DbContextHolder.setDataSource(value);
            }
//			方法注解可以覆盖类型注解
            Method m = clazz.getMethod(method.getName(), classes);
            if (m != null && m.isAnnotationPresent(DataSource.class)) {
                DataSource source = m.getAnnotation(DataSource.class);
                DbContextHolder.setDataSource(source.value());
            }
        } catch (Exception e) {
            log.error("clazz:{}\nDataSourceAspect error{}:", clazz, e);
        }
    }
}
