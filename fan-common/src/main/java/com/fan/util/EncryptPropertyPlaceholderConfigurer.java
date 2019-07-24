package com.fan.util;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;


/**
* @ClassName: EncryptPropertyPlaceholderConfigurer
* @Description: 该类用于对属性集中密码的数据进行加密解密
* @author fan
* @date 2018年7月26日
*
*/
@Slf4j
public class EncryptPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {
	//指定需要加密的属性
		private String[] propertyNames = {"redis.password","mongo.password","mysql.password","activemq.password","mysql2.password"};

		/**
		 * 解密指定propertyName的属性值
		 * @param propertyName
		 * @param propertyValue
		 * @return
		 */
		@Override
		protected String convertProperty(String propertyName, String propertyValue) {
			//过滤出需要解密的属性
			for (String p : propertyNames) {
				if (p.equalsIgnoreCase(propertyName)) {
					try {
						//返回AES解密后的字符串
//						log.info("密码进行解密 " + EncodeUtil.encodeBase64(SymmetricCryptoUtil.decryptAESWithDefaultKey(EncodeUtil.decodeBase64(propertyValue))));
						log.info("  密码进行解密 " + AESUtil.aesDecrypt(propertyValue, AESUtil.KEY));
//						return EncodeUtil.encodeBase64(SymmetricCryptoUtil.decryptAESWithDefaultKey(EncodeUtil.decodeBase64(propertyValue)));
						return AESUtil.aesDecrypt(propertyValue, AESUtil.KEY);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			return super.convertProperty(propertyName, propertyValue);
		}

}
