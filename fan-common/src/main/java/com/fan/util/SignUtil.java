package com.fan.util;

import com.google.common.collect.Lists;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * @author Achievo
 */
@Component
@Slf4j
public class SignUtil {

    private static final Logger logger = LoggerFactory.getLogger(SignUtil.class);

    private static final String charset = ConstantFan.CHARSET;

    protected static char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * base64签名
     *
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String getBase64(String input) throws UnsupportedEncodingException {
        byte[] bs = input.getBytes(charset);
        String encodeBytes = new String(Base64.getEncoder().encode(bs), charset);
        return encodeBytes;
    }

    /**
     * 时间戳倒序
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
        String str = "";
        try {
            str = URLEncoder.encode(s, charset);
        } catch (Exception e) {
            throw new java.lang.RuntimeException("encode error !");
        }
        return str;
    }

    /**
     * md5加密
     */
    public static String getMD5(String input) {
        String result = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            result = byte2hex(md.digest(input.getBytes(charset)));
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
    public static String md5Signature(TreeMap<String, String> params,
                                      String secret) {
        String result = null;
        StringBuffer orgin = getBeforeSign(params, new StringBuffer(secret));
        if (orgin == null) {
            return result;
        }
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            result = byte2hex(md.digest(orgin.toString().getBytes(charset)));
        } catch (Exception e) {
            throw new java.lang.RuntimeException("sign error !");
        }
        return result;
    }

    /**
     * 二行制转字符串
     */
    private static String byte2hex(byte[] b) {
        StringBuffer hs = new StringBuffer();
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) {
                hs.append("0").append(stmp);
            } else {
                hs.append(stmp);
            }
        }
        return hs.toString().toUpperCase();
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

    private static StringBuffer getBeforeSign(TreeMap<String, String> params,
                                              StringBuffer orgin) {
        if (params == null) {
            return null;
        }
        Map<String, String> treeMap = new TreeMap<String, String>();
        treeMap.putAll(params);
        Iterator<String> iter = treeMap.keySet().iterator();
        while (iter.hasNext()) {
            String name = (String) iter.next();
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
    public static String afterSortJson(String input) {
        // TreeMap默认对key进行排序
        TreeMap<Integer, String> map = new TreeMap<Integer, String>();
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
        if (StringUtils.isBlank(shaValue)) {
            return shaValue;
        }
        String encodeStr = "";
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(shaValue.getBytes("UTF-8"));
            encodeStr = byte2hex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            logger.error("getSha256 error:{}", e);
        } catch (UnsupportedEncodingException e) {
            logger.error("getSha256 error:{}", e);
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


    public static String SHA1(Map<String, Object> maps) throws DigestException {
        //获取信息摘要 - 参数字典排序后字符串
        String decrypt = getOrderByLexicographic(maps);
        try {
            return SHA1(decrypt);
        } catch (Exception e) {
            log.error("SHA1Util error:", e);
            throw new DigestException("签名错误！");
        }
    }

    /**
     * SHA1签名
     *
     * @param decrypt
     * @return
     * @throws DigestException
     */
    public static String SHA1(String decrypt) throws DigestException {
        if (null == decrypt || decrypt.length() == 0) {
            return null;
        }
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
            mdTemp.update(decrypt.getBytes(charset));

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
            throw new DigestException("SHA1 error!");
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