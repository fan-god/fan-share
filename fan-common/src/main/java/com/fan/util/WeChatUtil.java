package com.fan.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.util.*;

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

    /**
     * 生成带有 sign 的 XML 格式字符串
     *
     * @param data Map类型数据
     * @param key  API密钥
     * @return 含有sign字段的XML
     */
    public static String generateSignedXml(final Map<String, String> data, String key) throws Exception {
        return generateSignedXml(data, key, ConstantFan.MD5);
    }

    /**
     * 生成带有 sign 的 XML 格式字符串
     *
     * @param data     Map类型数据
     * @param key      API密钥
     * @param signType 签名类型
     * @return 含有sign字段的XML
     */
    public static String generateSignedXml(final Map<String, String> data, String key, String signType) throws Exception {
        String sign = generateSignature(data, key, signType);
        data.put(FieldConstant.WxPay.FIELD_SIGN, sign);
        return XmlUtil.mapToXml(data);
    }


    /**
     * 判断签名是否正确
     *
     * @param xmlStr XML格式数据
     * @param key    API密钥
     * @return 签名是否正确
     * @throws Exception
     */
    public static boolean isSignatureValid(String xmlStr, String key) throws Exception {
        Map<String, String> data = XmlUtil.xmlToMap(xmlStr);
        if (!data.containsKey(FieldConstant.WxPay.FIELD_SIGN)) {
            return false;
        }
        String sign = data.get(FieldConstant.WxPay.FIELD_SIGN);
        return generateSignature(data, key).equals(sign);
    }

    /**
     * 判断签名是否正确，必须包含sign字段，否则返回false。使用MD5签名。
     *
     * @param data Map类型数据
     * @param key  API密钥
     * @return 签名是否正确
     * @throws Exception
     */
    public static boolean isSignatureValid(Map<String, String> data, String key) throws Exception {
        return isSignatureValid(data, key, ConstantFan.MD5);
    }

    /**
     * 判断签名是否正确，必须包含sign字段，否则返回false。
     *
     * @param data     Map类型数据
     * @param key      API密钥
     * @param signType 签名方式
     * @return 签名是否正确
     * @throws Exception
     */
    public static boolean isSignatureValid(Map<String, String> data, String key, String signType) throws Exception {
        if (!data.containsKey(FieldConstant.WxPay.FIELD_SIGN)) {
            return false;
        }
        String sign = data.get(FieldConstant.WxPay.FIELD_SIGN);
        return generateSignature(data, key, signType).equals(sign);
    }

    /**
     * 生成签名
     *
     * @param data 待签名数据
     * @param key  API密钥
     * @return 签名
     */
    public static String generateSignature(final Map<String, String> data, String key) throws Exception {
        return generateSignature(data, key, ConstantFan.MD5);
    }

    /**
     * 生成签名. 注意，若含有sign_type字段，必须和signType参数保持一致。
     *
     * @param data     待签名数据
     * @param key      API密钥
     * @param signType 签名方式
     * @return 签名
     */
    public static String generateSignature(final Map<String, String> data, String key, String signType) throws Exception {
        Set<String> keySet = data.keySet();
        String[] keyArray = keySet.toArray(new String[keySet.size()]);
        Arrays.sort(keyArray);
        StringBuilder sb = new StringBuilder();
        for (String k : keyArray) {
            if (k.equals(FieldConstant.WxPay.FIELD_SIGN)) {
                continue;
            }
            if (data.get(k).trim().length() > 0) // 参数值为空，则不参与签名
                sb.append(k).append("=").append(data.get(k).trim()).append("&");
        }
        sb.append("key=").append(key);
        if (ConstantFan.MD5.equals(signType)) {
            return SignUtil.getMD5(sb.toString());
        } else if (ConstantFan.HMACSHA256.equals(signType)) {
            return SignUtil.getSHA256(sb.toString(), key);
        } else {
            throw new Exception(String.format("Invalid sign_type: %s", signType));
        }
    }

    /**
     * 获取当前时间戳，单位秒
     *
     * @return
     */
    public static long getCurrentTimestamp() {
        return System.currentTimeMillis() / 1000;
    }

    public static DocumentBuilder newDocumentBuilder() throws ParserConfigurationException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        documentBuilderFactory.setFeature("http://xml.org/sax/features/external-general-entities", false);
        documentBuilderFactory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
        documentBuilderFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        documentBuilderFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        documentBuilderFactory.setXIncludeAware(false);
        documentBuilderFactory.setExpandEntityReferences(false);

        return documentBuilderFactory.newDocumentBuilder();
    }

    public static Document newDocument() throws ParserConfigurationException {
        return newDocumentBuilder().newDocument();
    }
}
