package com.fan.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.AlgorithmParameters;
import java.security.Key;
import java.security.Security;
import java.util.Arrays;
import java.util.Properties;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * @author fan
 * @ClassName: AESUtil
 * @Description: AES加密和解密的工具类
 * @date 2018年7月17日
 */
@Component
public class AESUtil {
    private static boolean initialized = false;
    /**
     * 密钥 abcdefgabcdefg12
     */
//	public static String KEY;
    public static String KEY = "abcdefgabcdefg12";

    static {
        if (StringUtils.isBlank(KEY)) {
            try {
                PropertiesUtil pu = new PropertiesUtil("/config/freemark.properties");
                KEY = pu.readProperty(FieldConstant.AES_PASS);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 算法
     */
    private static final String PKCS5Padding = "AES/ECB/PKCS5Padding";
    private static final String PKCS7Padding = "AES/CFB/PKCS7Padding";

    public static void main(String[] args) throws Exception {
        String content = "wangfan789";

        System.out.println("加密前：" + content);

        System.out.println("加密密钥和解密密钥：" + KEY);

        String encrypt = aesEncrypt(content, KEY);
        System.out.println("加密后：" + encrypt);

        String decrypt = aesDecrypt(encrypt, KEY);
        System.out.println("解密后：" + decrypt);
    }

    /**
     * aes解密
     *
     * @param input 内容
     * @return
     * @throws Exception
     */
    public static String aesDecrypt(String input) throws Exception {
        return aesDecrypt(input, KEY);
    }

    /**
     * aes加密
     *
     * @param input
     * @return
     * @throws Exception
     */
    public static String aesEncrypt(String input) throws Exception {
        return aesEncrypt(input, KEY);
    }

    /**
     * 将byte[]转为各种进制的字符串
     *
     * @param bytes byte[]
     * @param radix 可以转换进制的范围，从Character.MIN_RADIX到Character.MAX_RADIX，超出范围后变为10进制
     * @return 转换后的字符串
     */
    public static String binary(byte[] bytes, int radix) {
        return new BigInteger(1, bytes).toString(radix);// 这里的1代表正数
    }

    /**
     * AES加密
     *
     * @param content    待加密的内容
     * @param encryptKey 加密密钥
     * @return 加密后的byte[]
     * @throws Exception
     */
    public static byte[] aesEncryptToBytes(String content, String encryptKey) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance(ConstantFan.AES);
        kgen.init(128);
        Cipher cipher = Cipher.getInstance(PKCS5Padding);
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encryptKey.getBytes(), ConstantFan.AES));

        return cipher.doFinal(content.getBytes(ConstantFan.CHARSET));
    }


    /**
     * AES加密为base 64 code
     *
     * @param content    待加密的内容
     * @param encryptKey 加密密钥
     * @return 加密后的base 64 code
     * @throws Exception
     */
    public static String aesEncrypt(String content, String encryptKey) throws Exception {
        return EncodeUtil.encodeBase64(aesEncryptToBytes(content, encryptKey));
    }

    /**
     * AES解密
     *
     * @param encryptBytes 待解密的byte[]
     * @param decryptKey   解密密钥
     * @return 解密后的String
     * @throws Exception
     */
    public static String aesDecryptByBytes(byte[] encryptBytes, String decryptKey) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance(ConstantFan.AES);
        kgen.init(128);

        Cipher cipher = Cipher.getInstance(PKCS5Padding);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(decryptKey.getBytes(), ConstantFan.AES));
        byte[] decryptBytes = cipher.doFinal(encryptBytes);

        return new String(decryptBytes);
    }

    /**
     * 将base 64 code AES解密
     *
     * @param encryptStr 待解密的base 64 code
     * @param decryptKey 解密密钥
     * @return 解密后的string
     * @throws Exception
     */
    public static String aesDecrypt(String encryptStr, String decryptKey) throws Exception {
        return StringUtils.isEmpty(encryptStr) ? null : aesDecryptByBytes(EncodeUtil.decodeBase64(encryptStr), decryptKey);
    }


    ////////////////////////////////////////////////TODO 以下内容为后来加上的还没验证////////////////////////////////////////////////////////////
    /**
     * 敏感数据对称解密
     *
     * @param input ——待加密的数据
     * @param iv  ——偏移量
     * @return
     */
    public static String encrypt(String input) throws Exception {
        initialize();
        Cipher cipher = Cipher.getInstance(PKCS7Padding);
        Key sKeySpec = new SecretKeySpec(KEY.getBytes(ConstantFan.CHARSET), ConstantFan.AES);
        // 初始化
        cipher.init(Cipher.ENCRYPT_MODE, sKeySpec, generateIV());
        byte[] result = cipher.doFinal(input.getBytes(ConstantFan.CHARSET));
        return new String(result);
    }

    /**
     * @return java.security.AlgorithmParameters
     * @Description 生成iv
     * @Param [iv]
     **/
    private static AlgorithmParameters generateIV() throws Exception {
        AlgorithmParameters params = AlgorithmParameters.getInstance(ConstantFan.AES);
        final byte[] iv = new byte[16];
        Arrays.fill(iv, (byte) 0x00);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        params.init(ivParameterSpec);
        return params;
    }

    /**
     * 添加算法
     */
    private static void initialize() {
        if (initialized) return;
        Security.addProvider(new BouncyCastleProvider());
        initialized = true;
    }
}
