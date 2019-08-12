package com.fan.util;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author fan
 * @title: WeChatUtil
 * @projectName fan-share
 * @description: TODO
 * @date 2019/8/12/00129:10
 */
@Slf4j
public class WeChatUtil {

    /**
     * 连接订单参数，空则忽略，连接符&
     * 使用详解：符合条件的参数按字段名称由小到大（字典顺序）排序，并连接
     *
     * @param o
     * @return
     */
    public static String concatOrderParams(Object o){
        TreeMap<String, String> tree = new TreeMap<>(); //用于排序
        Class clazz = o.getClass();
        Field[] fields = clazz.getDeclaredFields();

        //查找字段
        for (Field field : fields) {
            field.setAccessible(true);
            String fieldName = field.getName();
            String methodName = getFiledMethodName(fieldName);
            try {
                Method method = clazz.getMethod(methodName);
                Object value = method.invoke(o);
                if (value != null) {//不为空
                    tree.put(fieldName, value.toString());
                }
            } catch (NoSuchMethodException e) {
                log.error(e.getMessage());
            } catch (IllegalAccessException e) {
                log.error(e.getMessage());
            } catch (InvocationTargetException e) {
                log.error(e.getMessage());
            }
        }
        if (tree.size() == 0) {
            return null;
        }
        String str = linkMapKeyValue(tree, "&");

        return str.substring(1);//截取第一个&符号之后的内容
    }



    /**
     * 连接字符串:
     * * 将map值连接为key = value & key = value……形式
     *
     * @param map
     * @param character 连接符号，如：&
     * @return
     */
    public static String linkMapKeyValue(TreeMap<String, String> map, String character) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        Set<String> keys = map.keySet();
        Iterator<String> it = keys.iterator();
        while (it.hasNext()) {
            String key = it.next();
            sb.append(character + key + "=" + map.get(key));
        }
        return sb.toString();
    }


    /**
     * 获取字段方法名称
     *
     * @param fieldName
     * @return
     */
    public static String getFiledMethodName(String fieldName) {
        char firstChar = fieldName.toCharArray()[0];
        return "get" + String.valueOf(firstChar).toUpperCase() + fieldName.substring(1, fieldName.length());
    }

}
