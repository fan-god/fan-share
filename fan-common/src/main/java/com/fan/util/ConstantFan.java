package com.fan.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author fan
 * @ClassName: ConstantFan
 * @Description: 该 类用于存取系统常量
 * @date 2018年7月24日
 */
public interface ConstantFan {

    /**
     * @Fields 通用常量
     */
    String CHARSET = "UTF-8";
    String PROTOBUF = "protobuf";
    String SESSION = "session";
    String MESSAGE = "message";
    String EMPTY_STR = "";
    String EMPTY_JSON = "{}";
    //文件后缀
    String XLSX = ".xlsx";
    String XLS = ".xls";
    String JPG = ".jpg";
    String PNG = ".png";
    String JSON = "json";
    String XML = "xml";
    //成功
    String SUCCESS = "success";
    //失败
    String FAIL = "fail";
    //OK
    String OK = "ok";
    String YES = "yes";
    String NO = "no";
    String ID = "id";
    String REQUEST_METHOD_POST = "POST";
    String REQUEST_METHOD_GET = "GET";
    String REQUEST_METHOD_INPUT = "INPUT";
    String REQUEST_METHOD_PUT = "PUT";
    /**
     * 签名方式
     */
    String SHA1 = "SHA-1";
    String SHA256 = "SHA-256";
    String MD5 = "MD5";
    String AES = "AES";
    String HMACSHA256 = "HmacSHA256";
    /**
     * reids对应常量
     */
    //redis下标
    int DBINDEX_0 = 0;
    int DBINDEX_1 = 1;
    int DBINDEX_2 = 2;
    int DBINDEX_3 = 3;
    int DBINDEX_4 = 4;
    int DBINDEX_5 = 5;

    String[] REDIS_MAP = {};
    String REDIS_SESSION_INDEX = "sessionIndex";
    String REDIS_SESSION_OUTTIME = "sessionOutTime";
    String REDIS_ARGS_INDEX = "argsIndex";
    String REDIS_ARGS_OUTTIME = "argsOutTime";
    String REDIS_CRM_INDEX = "crmIndex";
    String REDIS_CRM_OUTTIME = "crmOutTime";
    String HOME_ERROR = "error";
    //检测session下标库是否发生了改变
    boolean SESSION_IS_UPDATE = false;
    //秒数
    Integer SESSION_SECOND = 60;
    Map<String, String> devDicMap = new HashMap<String, String>() {
        private static final long serialVersionUID = 1L;

        {
            put("", "");
        }
    };
    /**
     * 定时器分组
     * JOB_GROUP_DEFAULT 任务组名
     * TRIGGER_GROUP_DEFAULT 触发器组名
     */
    String JOB_GROUP_DEFAULT = "server1";
    String TRIGGER_GROUP_DEFAULT = "server1";


    /**
     * --------------------日期格式常量--------------------
     */
    String DATE_PATTERN_8 = "yyyyMMdd";
    String DATE_PATTERN_10 = "yyyy-MM-dd";
    String DATE_PATTERN_14 = "yyyyMMddHHmmss";
    String DATE_PATTERN_17 = "yyyyMMddHHmmssSSS";
    String DATE_PATTERN_19 = "yyyy-MM-dd HH:mm:ss";
    String DATE_PATTERN_19_2 = "yyyy/MM/dd HH:mm:ss";
    /**
     * --------------------日期格式常量--------------------
     */

    /*************************API******************************/
    String APINAME = "apiName";
    String APPKEY = "appkey";
    String SIGN = "apiName";
    String TIMESTAMP = "timestamp";
    String API_EXEC_S = "success";
    String API_EXEC_F = "fail";

    String CONTENT_TYPE = "application/json";


    /******************************对外接口版本控制验证版本正则表达式***********************************/
    String VERSION_PREFIX_PATTERN = "v([+-]?\\d*\\.\\d*)";

    /***************************************Netty***************************************/
    //ping和pong类型的数据
    byte PING = 1;
    byte PONG = 2;
    //顾客
    byte CUSTOMER = 3;

    /*****************************导入导出文件***********************************/
    int SHEET_NO = 1;
    String SHEET_NAME = "sheet1";
    int HEAD_LINE_MUN = 0;
    //导出文件路径
    String EXPORT_FILE_PATH = "D:\\fan-share\\exportFiles\\";
    //导入文件路径
    String IMPORT_FILE_PATH = "D:\\fan-share\\importFiles\\";
    //生成二维码路径
    String QRCODE_PATH = "D:\\fan-share\\qrcode\\";
    Integer BASE_DAY_0F_LONG = 24 * 60 * 60 * 1000;

    /*****************************登录******************************/
    String USER_SESSION = "userSession";

    /*****************************爬虫*********************************/
    String SPIDER_TARGET_URL = "https://www.missfresh.cn/";
//    String SPIDER_TARGET_URL = "http://www.netbian.com/meinv/index_201.htm";
//    String SPIDER_TARGET_URL = "https://tom712.com";
}
