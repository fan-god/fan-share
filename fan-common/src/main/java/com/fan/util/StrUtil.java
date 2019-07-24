package com.fan.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.Random;
@Slf4j
public class StrUtil {
    public static String getRandomStr(int len) throws Exception {
        if (len > 0) {
            String baseStr = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
            StringBuffer sb = new StringBuffer("");
            for (int i = 0; i < len; i++) {
                char c = baseStr.charAt(new Random().nextInt(baseStr.length()));
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
     * 批量校验参数
     *
     * @param params
     */
    public static String checkParams(Map<String, String> params) {
        for (String key : params.keySet()) {
            if (StringUtils.isBlank(key)) {
                String message1 = InternationalUtil.getMessage(LangConstant.Common.request_param.getKey());
                String message2 = InternationalUtil.getMessage(LangConstant.Common.no_empty.getKey());
                return String.format("%s[%s]%s", message1, params.get(key), message2);
            }
        }
        return FieldConstant.SUCCESS;
    }
}
