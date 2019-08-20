package com.fan.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.security.SecureRandom;
import java.util.Map;
@Slf4j
public class StrUtil {
    public static String getRandomStr(int len) throws Exception {
        if (len > 0) {
            String baseStr = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
            StringBuffer sb = new StringBuffer("");
            for (int i = 0; i < len; i++) {
                char c = baseStr.charAt(new SecureRandom().nextInt(baseStr.length()));
                sb.append(c);
            }
            return sb.toString();
        }
        throw new Exception("生成随机字符串长度必须大于0");
    }

    /**
     * 打印执行时间
     *
     * @param startTime
     * @param endTime
     * @param inter
     */
    public static void printTime(Long startTime, Long endTime, String inter, String server) {
        String logStr = String.format("%s [%s] execution time %dms", inter, server, endTime - startTime);
        log.info(logStr);
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
