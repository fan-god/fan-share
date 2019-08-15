package com.fan.util;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.support.RequestContextUtils;

import java.util.*;

/**
 * Created by fan on 2018/9/5.
 * <p>
 * 国际化配置工具类
 */
@Slf4j
public class InternationalUtil {
    //缓存
    private static Map<String, String> zh_CN_map = Maps.newHashMap();
    private static Map<String, String> en_US_map = Maps.newHashMap();
    private static Map<String, String> ja_JP_map = Maps.newHashMap();
    private static Map<String, String> zh_HK_map = Maps.newHashMap();


    /**
     * 从国际化资源配置文件中根据key获取value  方法一
     * TODO 暂时不用
     *
     * @param key
     * @return
     */
    public static String getMessageOld(String key) {
        try {
            Locale currentLocale = RequestContextUtils.getLocale(HttpCilentUtil.getRequest());
            ResourceBundle bundle = ResourceBundle.getBundle(LangConstant.BASENAME, currentLocale);

            String lang = currentLocale.toString();
            //中文
            String zh_CN = LangConstant.Country.zh_CN.name();
            //英文
            String en_US = LangConstant.Country.en_US.name();
            //日语
            String ja_JP = LangConstant.Country.ja_JP.name();
            //中文繁体
            String zh_HK = LangConstant.Country.zh_HK.name();

            if (zh_CN.equals(lang)) {
                initMap(bundle, zh_CN_map);
                return zh_CN_map.get(key);
            } else if (en_US.equals(lang)) {
                initMap(bundle, en_US_map);
                return en_US_map.get(key);
            } else if (ja_JP.equals(lang)) {
                initMap(bundle, ja_JP_map);
                return ja_JP_map.get(key);
            } else if (zh_HK.equals(lang)) {
                initMap(bundle, zh_HK_map);
                return zh_HK_map.get(key);
            }
            return null;
        } catch (Exception e) {
            log.error("InternationalUtil getMessage error{}:", e);
            return null;
        }
    }

    /**
     * 加载缓存
     * TODO 暂时不用
     *
     * @return
     */
    private static void initMap(final ResourceBundle bundle, final Map<String, String> map) {
        try {
            if (map.isEmpty()) {
                Set<String> keys = bundle.keySet();
                keys.forEach(key -> map.put(key, bundle.getString(key)));
            }
        } catch (Exception e) {
            log.error("InternationalUtil initMap error{}:", e);
        }
    }

    /**
     * 从国际化资源配置文件中根据key获取value  方法一
     *
     * @param key
     * @return
     */
    public static String getMessage(String key) {
        Locale currentLocale = RequestContextUtils.getLocale(HttpCilentUtil.getRequest());
        ResourceBundle bundle = ResourceBundle.getBundle(LangConstant.BASENAME, currentLocale);
        return bundle.getString(key);
    }
}
