package com.fan.util;

import org.springframework.web.servlet.support.RequestContextUtils;

import java.util.*;

/**
 * Created by Achievo on 2018/9/5.
 * <p>
 * 国际化配置工具类
 */
public class InternationalUtil {
    //中文缓存
    public static Map<String, String> ZH_map = new HashMap<>();
    //英文缓存
    public static Map<String, String> EN_map = new HashMap<>();


    /**
     * 从国际化资源配置文件中根据key获取value  方法一
     *
     * @param key
     * @return
     */
    public static String getMessage(String key) {
        try {
            Locale currentLocale = RequestContextUtils.getLocale(HttpCilentUtil.getRequest());
            ResourceBundle bundle = ResourceBundle.getBundle(LangConstant.BASENAME, currentLocale);

            String lang = currentLocale.getLanguage();
            //中文
            String zh = LangConstant.Country.CHINA.getName();
            //英文
            String en = LangConstant.Country.ENGLISH.getName();
            if (zh.equals(lang)) {
                initZH_map(bundle);
                return ZH_map.get(key);
            } else if (en.equals(lang)) {
                initEN_map(bundle);
                return EN_map.get(key);
            }
            return null;
        } catch (Exception e) {
            LoggerUtil.error("InternationalUtil getMessage error{}:" + e);
            return null;
        }
    }

    /**
     * 加载中文缓存
     *
     * @return
     */
    private static void initZH_map(ResourceBundle bundle) {
        try {
            if (ZH_map.isEmpty()) {
                Set<String> keys = bundle.keySet();
                for (String key : keys) {
                    ZH_map.put(key, bundle.getString(key));
                }
            }
        } catch (Exception e) {
            LoggerUtil.error("InternationalUtil initZH_map error{}:" + e);
        }
    }

    /**
     * 加载英文缓存
     *
     * @return
     */
    private static void initEN_map(ResourceBundle bundle) {
        try {
            if (EN_map.isEmpty()) {
                Set<String> keys = bundle.keySet();
                for (String key : keys) {
                    EN_map.put(key, bundle.getString(key));
                }
            }
        } catch (Exception e) {
            LoggerUtil.error("InternationalUtil initEN_map error{}:" + e);
        }
    }
}
