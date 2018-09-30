package com.fan.util;

import com.google.gson.*;

import java.util.ArrayList;
import java.util.List;

public class JsonUtil {
    private static final Gson gson = new GsonBuilder().setDateFormat("yyyyMMddHHmmss").create();

    public static JsonObject getJObject(String input) {
        try {
            JsonElement jsonEl = new JsonParser().parse(input);
            JsonObject jsonObj = jsonEl.getAsJsonObject();// 转换成Json对象
            return jsonObj;
        } catch (Exception e) {
            LoggerUtil.error("JsonUtil getJObject error{}:" + e);
            return null;
        }
    }

    /**
     * 对象转json
     *
     * @param obj
     * @return
     */
    public static String beanToJson(Object obj) {
        return gson.toJson(obj);
    }

    /**
     * json添加元素
     *
     * @param key
     * @param value
     * @param jObject
     */
    public static void addElement(String key, String value, JsonObject jObject) {
        jObject.addProperty(key, value);
    }

    public static <T> T jsonTobean(Class<T> c, String json) {
        return gson.fromJson(json, c);
    }

    public static <T> List<T> jsonToList(String data, Class<T> c) {
        try {

            List<T> list = new ArrayList<>();
            JsonElement jsonEl = new JsonParser().parse(data);
            JsonArray jsonArray = jsonEl.getAsJsonArray();// 转换成Json数组
            for (JsonElement element : jsonArray) {
                list.add((T) gson.fromJson(element, c));
            }
            if (list.isEmpty()) {
                return null;
            }
            return list;
        } catch (Exception e) {
            LoggerUtil.error("json数组转换异常" + e);
            return null;
        }
    }

    public static Object getValByKey(JsonObject jo, String key, Class<?> c) {
        try {
            if (c.equals(int.class) || c.equals(Integer.class)) {
                return jo.get(key).getAsInt();
            } else if (c.equals(String.class)) {
                return jo.get(key).getAsString();
            } else if (c.equals(double.class) || c.equals(Double.class)) {
                return jo.get(key).getAsDouble();
            } else if (c.equals(long.class) || c.equals(Long.class)) {
                return jo.get(key).getAsLong();
            }
        } catch (Exception e) {
            return null;
        }
        return null;
    }
}
