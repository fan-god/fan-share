package com.fan.util;

import java.io.*;
import java.util.Properties;

/**
 * 读取properties文件的工具类
 * 
 * @author fliay
 *
 */
public class PropertiesUtil {
	private String propertiesName = "";// 设置配置文件名称

	public PropertiesUtil() {

	}

	public PropertiesUtil(String fileName) {
		this.propertiesName = fileName;
	}

	/**
	 * 通过配置文件读取
	 * 
	 * @param key
	 * @return
	 */
	public String readProperty(String key) {
		String value = "";
		InputStream is = null;
		try {
			is = PropertiesUtil.class.getClassLoader().getResourceAsStream(propertiesName);
			Properties p = new Properties();
			p.load(is);
			value = p.getProperty(key);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return value;

	}

	/**
	 * 获取配置文件
	 * 
	 * @return
	 */
	public Properties getProperties() {
		Properties p = new Properties();
		InputStream is = null;
		try {
			is = PropertiesUtil.class.getClassLoader().getResourceAsStream(propertiesName);
			p.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return p;
	}

	public void writeProperties(String key, String value) {
		InputStream is = null;
		OutputStream os = null;
		Properties p = new Properties();
		try {
			is = new FileInputStream(propertiesName);
			p.load(is);
			os = new FileOutputStream(PropertiesUtil.class.getClassLoader().getResource(propertiesName).getFile());
			p.setProperty(key, value);
			p.store(os, key);
			os.flush();
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != is)
					is.close();
				if (null != os)
					os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	public static void main(String[] args) {

		PropertiesUtil p = new PropertiesUtil("interfaceInfo.properties");
		String url = p.readProperty("url");
		String secret = p.readProperty("secret");
		System.out.println(url + "," + secret);

	}

}
