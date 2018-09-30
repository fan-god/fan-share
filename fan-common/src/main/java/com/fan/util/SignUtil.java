package com.fan.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.Map.Entry;

/**
 * 签名工具类
 */

/**
 * 
 * @author Achievo
 *
 */
@Component
public class SignUtil {

	private static final Logger logger = LoggerFactory.getLogger(SignUtil.class);

	private static final String charset = "UTF-8";

	protected static char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

	/**
	 * base64签名
	 *
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String getBase64(String input)
			throws UnsupportedEncodingException {
		byte[] bs = input.getBytes(charset);
		String encodeBytes = Base64.encodeBase64String(bs);
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
			throw new RuntimeException("encode error !");
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
			throw new RuntimeException("sign error !");
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
			throw new RuntimeException("sign error !");
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
			stmp = (Integer.toHexString(b[n] & 0XFF));
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
			LoggerUtil.error("json数据中字段为空");
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
	 * 有方物联卡接口签名
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
		for (Entry<String, String> entry : params.entrySet()) {
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
	 * 中景元物联卡接口签名
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
		for (Entry<String, String> entry : params.entrySet()) {
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
		if(StringUtils.isBlank(shaValue)){
			return shaValue;
		}
		String encodeStr = "";
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("SHA-256");
			messageDigest.update(shaValue.getBytes("UTF-8"));
			encodeStr = byte2hex(messageDigest.digest());
		} catch (NoSuchAlgorithmException e) {
			logger.error("getSha256 error:{}",e);
		} catch (UnsupportedEncodingException e) {
			logger.error("getSha256 error:{}",e);
		}
		return encodeStr;
	}

	/**
	 * 获取文件SHA256码,小写
	 * @return String
	 */
	public static String getSha256ToLowerCase(String shaValue) {
		String encodeStr = getSha256(shaValue);
		if(StringUtils.isNotBlank(encodeStr)){
			encodeStr = encodeStr.toLowerCase();
		}
		return encodeStr;
	}

}