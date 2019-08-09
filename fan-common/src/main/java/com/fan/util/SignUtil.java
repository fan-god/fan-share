package com.fan.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.DigestException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.Map.Entry;

/**
 * 签名工具类
 */

/**
 * @author fan
 */
@Component
@Slf4j
public class SignUtil {

    protected static char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    private static String token = "fan_sys_token";

    /**
     * 微信签名校验
     *
     * @param signature 微信签名
     * @param timestamp 微信token
     * @param nonce
     * @return
     */
    public static boolean checkWxSignature(String signature, String timestamp, String nonce) {
        String checkText = null;
        try {
            String[] params = {token, timestamp, nonce};
            Arrays.sort(params);
            String content = params[0].concat(params[1]).concat(params[2]);
            MessageDigest md = MessageDigest.getInstance(ConstantFan.SIGN_TYPE_SHA1);
            byte[] bytes = md.digest(content.toString().getBytes());
            checkText = byte2hexLowerCase(bytes);
        } catch (Exception e) {
            log.error("checkWxSignature error:{}", e);
        }
        return checkText != null ? checkText.equalsIgnoreCase(signature) : false;
    }


    /**
     * base64签名
     *
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String getBase64(String input) throws UnsupportedEncodingException {
        byte[] bs = input.getBytes(ConstantFan.CHARSET);
        String base64Str = new String(Base64.getEncoder().encode(bs), ConstantFan.CHARSET);
        return base64Str;
    }

    /**
     * 获取倒序时间戳
     *
     * @return
     */
    public static String getReversedTimestamp() {
        StringBuffer sb = new StringBuffer(new Date().getTime() + "");
        return sb.reverse().toString();
    }

    /**
     * URL加密
     *
     * @throws UnsupportedEncodingException
     */
    public static String encodeUri(String s) {
        String str;
        try {
            str = URLEncoder.encode(s, ConstantFan.CHARSET);
        } catch (Exception e) {
            throw new java.lang.RuntimeException("encode error !");
        }
        return str;
    }

    /**
     * md5加密
     */
    public static String getMD5(String input) {
        String result;
        try {
            MessageDigest md = MessageDigest.getInstance(ConstantFan.SIGN_TYPE_MD5);
            byte[] bytes = md.digest(input.getBytes(ConstantFan.CHARSET));
            result = byte2hex(bytes);
        } catch (Exception e) {
            throw new java.lang.RuntimeException("sign error !");
        }
        return result;
    }

    /**
     * 新的md5签名，首尾放secret。
     *
     * @param secret 分配给您的APP_SECRET
     */
    public static String md5Signature(TreeMap<String, String> params, String secret) {
        String result = null;
        try {
            StringBuffer orgin = getBeforeSign(params, new StringBuffer(secret));
            if (orgin == null) {
                return result;
            }
            MessageDigest md = MessageDigest.getInstance(ConstantFan.SIGN_TYPE_MD5);
            byte[] bytes = md.digest(orgin.toString().getBytes(ConstantFan.CHARSET));
            result = byte2hex(bytes);
        } catch (Exception e) {
            throw new java.lang.RuntimeException("sign error !" + e);
        }
        return result;
    }

    /**
     * 二进制转字符串(转16进制)，大写
     */
    private static String byte2hex(byte[] bytes) {
        StringBuffer hs = new StringBuffer();
        String s;
        try {
            for (int n = 0; n < bytes.length; n++) {
                s = (Integer.toHexString(bytes[n] & 0XFF));
                if (s.length() == 1) {
                    hs.append("0").append(s);
                } else {
                    hs.append(s);
                }
            }
        } catch (Exception e) {
            log.error("byte2hex error:{}", e);
        }
        return hs.toString().toUpperCase();
    }

    /**
     * 二进制转字符串(转16进制),小写
     */
    private static String byte2hexLowerCase(byte[] bytes) {
        return byte2hex(bytes).toLowerCase();
    }
    private static String bufferToHex(byte bytes[], int m, int n) {
        StringBuffer stringbuffer = new StringBuffer(2 * n);
        int k = m + n;
        for (int l = m; l < k; l++) {
            appendHexPair(bytes[l], stringbuffer);
        }
        return stringbuffer.toString();
    }


    private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
        char c0 = hexDigits[(bt & 0xf0) >> 4];
        char c1 = hexDigits[bt & 0xf];
        stringbuffer.append(c0);
        stringbuffer.append(c1);
    }

    /**
     * 添加参数的封装方法
     */
    private static StringBuffer getBeforeSign(TreeMap<String, String> params, StringBuffer orgin) {
        if (params == null) {
            return null;
        }
        Map<String, String> treeMap = new TreeMap<>();
        treeMap.putAll(params);
        Iterator<String> it = treeMap.keySet().iterator();
        while (it.hasNext()) {
            String name = it.next();
            orgin.append(name).append(params.get(name));
        }
        return orgin;
    }

    /**
     * 字段排序，按哈希值排序,升序,获取排序后的json
     *
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public static String sortJson(String input) {
        // TreeMap默认对key进行排序
        TreeMap<Integer, String> map = Maps.newTreeMap();
        JsonObject jsonObj = JsonUtil.getJObject(input);
        Set<Entry<String, JsonElement>> entrySet = jsonObj.entrySet();
        if (entrySet.size() == 0) {
            log.error("json数据中字段为空");
            return null;
        }
        for (Entry<String, JsonElement> entry : entrySet) {
            if (entry.getValue() != null) {
                map.put(entry.getKey().hashCode(), entry.getKey());
            }
        }
        StringBuffer sb = new StringBuffer();
        sb.append("params={");
        for (Integer mapKey : map.keySet()) {
            String str = map.get(mapKey);
            sb.append("\"" + str + "\":" + jsonObj.get(str) + ",");
        }
        sb.append("}");
        return sb.toString();
    }


    /**
     * 接口签名
     *
     * @param params
     * @param key
     * @return
     */
    public static String getSignLowerCase(Map<String, String> params, String key) {
        String sign = null;
        if (params == null || params.isEmpty()) {
            return sign;
        }
        if (params.containsKey("sign")) {
            params.remove("sign");
        }

        StringBuilder keyValueBuff = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            keyValueBuff.append(entry.getKey().trim()).append("=").append(entry.getValue().trim()).append("&");
        }
        keyValueBuff.deleteCharAt(keyValueBuff.length() - 1);
        if (StringUtils.isNotBlank(key)) {
            keyValueBuff.append(key);
        }
        String keyValueStr = keyValueBuff.toString();
        sign = getMD5(keyValueStr);

        return sign.toLowerCase();
    }

    /**
     * 接口签名
     *
     * @param params
     * @param key
     * @return
     */
    public static String getZJYSignLowerCase(Map<String, String> params, String key) {
        String sign = null;
        if (params == null || params.isEmpty()) {
            return sign;
        }
        if (params.containsKey("sign")) {
            params.remove("sign");
        }

        StringBuilder keyValueBuff = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            keyValueBuff.append(entry.getValue().trim());
        }
        if (StringUtils.isNotBlank(key)) {
            keyValueBuff.append(key);
        }
        String keyValueStr = keyValueBuff.toString();
        sign = getMD5(keyValueStr);

        return sign.toLowerCase();
    }


    /**
     * 获取文件SHA256码
     *
     * @return String
     */
    public static String getSha256(String shaValue) {
        String encodeStr = "";
        try {
            if (StringUtils.isBlank(shaValue)) {
                return shaValue;
            }
            MessageDigest messageDigest = MessageDigest.getInstance(ConstantFan.SIGN_TYPE_SHA256);
            messageDigest.update(shaValue.getBytes(ConstantFan.CHARSET));
            encodeStr = byte2hex(messageDigest.digest());
        } catch (Exception e) {
            log.error("getSha256 error:{}", e);
        }
        return encodeStr;
    }

    /**
     * 获取文件SHA256码,小写
     *
     * @return String
     */
    public static String getSha256ToLowerCase(String shaValue) {
        String encodeStr = getSha256(shaValue);
        if (StringUtils.isNotBlank(encodeStr)) {
            encodeStr = encodeStr.toLowerCase();
        }
        return encodeStr;
    }

    /**
     * 参数排序后的SHA1签名
     *
     * @param maps
     * @return
     */
    public static String SHA1(Map<String, Object> maps) {
        //获取信息摘要 - 参数字典排序后字符串
        String decrypt = getOrderByLexicographic(maps);
        try {
            return SHA1(decrypt);
        } catch (Exception e) {
            log.error("SHA1Util error:", e);
            return null;
        }
    }

    /**
     * SHA1签名
     *
     * @param decrypt
     * @return
     * @throws DigestException
     */
    public static String SHA1(String decrypt) {
        try {
            if (null == decrypt || decrypt.length() == 0) {
                return null;
            }
            MessageDigest mdTemp = MessageDigest.getInstance(ConstantFan.SIGN_TYPE_SHA1);
            mdTemp.update(decrypt.getBytes(ConstantFan.CHARSET));
            byte[] md = mdTemp.digest();
            int j = md.length;
            char[] buf = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(buf);
        } catch (Exception e) {
            log.error("SHA1Util error:{}", e);
            return null;
        }
    }

    /**
     * 获取参数的字典排序
     *
     * @param maps 参数key-value map集合
     * @return String 排序后的字符串
     */
    private static String getOrderByLexicographic(Map<String, Object> maps) {
        return splitParams(lexicographicOrder(getParamsName(maps)), maps);
    }

    /**
     * 获取参数名称 key
     *
     * @param maps 参数key-value map集合
     * @return
     */
    private static List<String> getParamsName(Map<String, Object> maps) {
        List<String> paramNames = Lists.newArrayList();
        for (Map.Entry<String, Object> entry : maps.entrySet()) {
            paramNames.add(entry.getKey());
        }
        return paramNames;
    }

    /**
     * 参数名称按字典排序
     *
     * @param paramNames 参数名称List集合
     * @return 排序后的参数名称List集合
     */
    private static List<String> lexicographicOrder(List<String> paramNames) {
        Collections.sort(paramNames);
        return paramNames;
    }

    /**
     * 拼接排序好的参数名称和参数值
     *
     * @param paramNames 排序后的参数名称集合
     * @param maps       参数key-value map集合
     * @return String 拼接后的字符串
     */
    private static String splitParams(List<String> paramNames, Map<String, Object> maps) {
        StringBuilder paramStr = new StringBuilder();
        for (String paramName : paramNames) {
            paramStr.append(paramName);
            for (Map.Entry<String, Object> entry : maps.entrySet()) {
                if (paramName.equals(entry.getKey())) {
                    paramStr.append(String.valueOf(entry.getValue()));
                }
            }
        }
        return paramStr.toString();
    }
}