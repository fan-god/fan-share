package com.fan.util;


import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

/**
 * Created by simon on 2017/6/21.
 * 编码工具类，封装了一些常见的编码实现
 */
public class EncodeUtil {

    /**
     * 十六进制 编码
     * @param data
     * @return
     */
    public static String encodeHex(byte[] data) {
        return Hex.encodeHexString(data);
    }

    /**
     * 十六进制 解码
     * @param hexStr
     * @return
     * @throws Exception
     */
    public static byte[] decodeHex(String hexStr) throws Exception {
        return Hex.decodeHex(hexStr.toCharArray());
    }

    /**
     * Base64 byte[] 转 String
     * @param data
     * @return
     */
    public static String encodeBase64(byte[] data) {
        return Base64.encodeBase64String(data);
    }

    /**
     * Base64 String转 byte[]
     * @param base64Str
     * @return
     */
    public static byte[] decodeBase64(String base64Str) {
        return Base64.decodeBase64(base64Str);
    }
}
