package com.fan.util;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 数据和实体类的相互转换
 *
 * @author fan
 */
@Slf4j
public class DataConvertUtil {
    /**
     * 对象转json或xml数据
     *
     * @param obj
     * @param format
     * @return
     */
    public static String beanToString(Object obj, String format) {
        try {
            if (format.equalsIgnoreCase(ConstantFan.JSON)) {
                return JsonUtil.beanToJson(obj);
            } else if (format.equalsIgnoreCase(ConstantFan.XML)) {
                return XmlUtil.beanToXml(obj);
            } else {
                throw new Exception("不支持的数据格式");
            }
        } catch (Exception e) {
            log.error("数据转换出错,错误信息:{}", e);
            return null;
        }
    }

    /**
     * 字符串转对象
     *
     * @param c
     * @param data
     * @param format
     * @return
     */
    public static <T> T stringToBean(Class<T> c, String data, String format) {
        try {
            if (format.equalsIgnoreCase(ConstantFan.JSON)) {
                return JsonUtil.jsonTobean(c, data);
            } else if (format.equalsIgnoreCase(ConstantFan.XML)) {
                return XmlUtil.xmlToBean(data);
            } else {
                throw new Exception("不支持的数据格式");
            }
        } catch (Exception e) {
            log.error("数据转换出错,错误信息:{}", e);
            return null;
        }
    }

    /**
     * 数据转对象集合
     *
     * @param data
     * @param format
     * @param c
     * @return
     */
    public static <T> List<T> stringToList(String data, String format, Class<T> c) {
        try {
            if (format.equalsIgnoreCase(ConstantFan.JSON)) {
                return JsonUtil.jsonToList(data, c);
            } else if (format.equalsIgnoreCase(ConstantFan.XML)) {
                // 暂时没写
                return null;
            } else {
                throw new Exception("不支持的数据格式");
            }
        } catch (Exception e) {
            log.error("数据转换出错,错误信息:{}", e);
            return null;
        }
    }

    /**
     * map转对象
     *
     * @param map
     * @param c
     * @param <T>
     * @return
     */
    public static <T> T mapToBean(Map<String, Object> map, Class<T> c) {
        try {
            T t = c.newInstance();
            BeanUtils.populate(t, map);
            return t;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 对象转map
     *
     * @param o
     * @return
     */
    public static Map<String, Object> beanToMap(Object o) {
        if (null == o) {
            return null;
        }
        return new BeanMap(o);
    }

    /**
     * 对象转map
     *
     * @param o
     * @return
     */
    public static TreeMap<String, String> beanToTreeMap(Object o) {
        TreeMap<String, String> tree = Maps.newTreeMap(); //用于排序
        Class clazz = o.getClass();
        Field[] fields = clazz.getDeclaredFields();
        try {
            //查找字段
            for (Field field : fields) {
                field.setAccessible(true);
                String fieldName = field.getName();
                String methodName = StrUtil.getFiledMethodName(fieldName);
                Method method = clazz.getMethod(methodName);
                Object value = method.invoke(o);
                if (value != null) {//不为空
                    tree.put(fieldName, value.toString());
                }
            }
            return tree;
        } catch (Exception e) {
            log.error("beanToTreeMap error:{}", e);
            return null;
        }
    }
}
