package com.fan.util;

import org.apache.log4j.Logger;

public class LoggerUtil {

	public static final Logger log = Logger.getLogger(LoggerUtil.class);

	public static void info(Object obj) {
		info(obj,ConstantAiot.LOG_WRITE_INFO);
	}

	public static void info(Object obj,boolean isInfo) {
		if(isInfo){
			log.info(obj);
		}
	}

	public static void error(Object obj) {
		error(obj,true);
	}

	public static void error(Object obj,boolean isWrror) {
		if(isWrror){
			log.error(obj);
		}
	}

	public static void debug(Object obj) {
		debug(obj,true);
	}

	public static void debug(Object obj,boolean isDebug) {
		if(isDebug){
			log.debug(obj);
		}
	}


	public static void warn(Object obj) {
		log.warn(obj);
	}

	public static void warn(Object obj,boolean isWarn) {
		if(isWarn){
			log.warn(obj);
		}
	}
}
