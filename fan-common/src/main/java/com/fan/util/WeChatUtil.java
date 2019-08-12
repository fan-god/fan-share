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
    public static String concatOrderParams(Object o) {
        TreeMap<String, String> tree = DataConvertUtil.beanToTreeMap(o);
        if (!tree.isEmpty()) {
            String str = linkMapKeyValue(tree, "&");
            return str.substring(1);//截取第一个&符号之后的内容
        }
        return null;
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
        sb.append(character).append("key=192006250b4c09247ec02edce69f6a2d");
        return sb.toString();
    }


}
