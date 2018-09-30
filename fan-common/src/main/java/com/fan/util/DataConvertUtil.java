package com.fan.util;

import java.util.List;

/**
 * 数据和实体类的相互转换
 *
 * @author Achievo
 */
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
            if (format.equalsIgnoreCase(ConstantAiot.JSON)) {
                return JsonUtil.beanToJson(obj);
            } else if (format.equalsIgnoreCase(ConstantAiot.XML)) {
                return XmlUtil.beanToXml(obj);
            } else {
                throw new Exception("不支持的数据格式");
            }
        } catch (Exception e) {
            LoggerUtil.error("数据转换出错,错误信息:" + e);
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
            if (format.equalsIgnoreCase(ConstantAiot.JSON)) {
                return JsonUtil.jsonTobean(c, data);
            } else if (format.equalsIgnoreCase(ConstantAiot.XML)) {
                return XmlUtil.xmlTobean(c, data);
            } else {
                throw new Exception("不支持的数据格式");
            }
        } catch (Exception e) {
            LoggerUtil.error("数据转换出错,错误信息:" + e);
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
            if (format.equalsIgnoreCase(ConstantAiot.JSON)) {
                return JsonUtil.jsonToList(data, c);
            } else if (format.equalsIgnoreCase(ConstantAiot.XML)) {
                // 暂时没写
                return null;
            } else {
                throw new Exception("不支持的数据格式");
            }
        } catch (Exception e) {
            LoggerUtil.error("数据转换出错,错误信息:" + e);
            return null;
        }
    }
}
