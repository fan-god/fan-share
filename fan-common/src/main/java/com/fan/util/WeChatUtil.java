package com.fan.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

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
            sb.append(character.concat(key).concat("=").concat(map.get(key)));
        }
        sb.append(character).append("key=192006250b4c09247ec02edce69f6a2d");
        return sb.toString();
    }

    /**
     * 接口签名
     *
     * @param params
     * @param key
     * @return
     */
    public static String getSignFromMap(TreeMap<String, String> params, String key) {
        String sign = null;
        if (params == null || params.isEmpty()) {
            return sign;
        }
        if (params.containsKey("sign")) {
            params.remove("sign");
        }
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            sb.append(entry.getKey().trim()).append("=").append(entry.getValue().trim()).append("&");
        }
        sb.deleteCharAt(sb.length() - 1);
        if (StringUtils.isNotBlank(key)) {
            sb.append("&key=").append(key);
        }
        String keyValueStr = sb.toString();
        System.out.println(keyValueStr);
        sign = SignUtil.getMD5(keyValueStr);
        return sign;
    }

    /**
     * 接口签名
     *
     * @param o
     * @param key
     * @return
     */
    public static String getSignFromMap(Object o, String key) {
        TreeMap<String, String> tree = DataConvertUtil.beanToTreeMap(o);
        return getSignFromMap(tree, key);
    }
}
