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
     * 记录info级别日志
     */
    Boolean LOG_WRITE_INFO = true;

    /**
     * @Fields EXPIRATION : 该属性作为reids中CRM过期时间的字段，存放在MongoDB中
     */
    String CRNEXPIRATION = "crnExpiration";

    /**
     * @Fields ARGSEXPIRATION :该属性作为reids中ARGS过期时间的字段，存放在MongoDB中
     */
    String ARGSEXPIRATION = "argsExpiration";

    String ARGS = "args";

     int ARGS_DB_INDEX = 3;

    String CRM = "crm";

    String CHARSET = "UTF-8";

    String JSON = "json";

    String PROTOBUF = "protobuf";

    String XML = "xml";

    String USER_CONTEXT = "userInfo";

    String SESSION = "session";

    String MESSAGE = "message";


    /**
     * reids对应的Map本地缓存
     */

    Map<String, Integer> redisDicMap = new HashMap<>();
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
    //首页默认显示条数
    Integer HOME_COUNT = 20;
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
     * 默认删除90以前的信息
     */
    int DELETE_SIMCARD_DAY_AMOUNT = -90;
    String DELETE_SIMCARD_DAY_AMOUNT_KEY = "delete.simcard.day.amount.key";

    /**
     * 是否记录日志
     */
    Boolean LOG_WRITE = true;
    String LOG_WRITE_KEY = "log.write.key";

    /**
     * 刷新所有
     */
    int REFRESH_ALL_SIMCARD = 0;
    String REFRESH_ALL_SIMCARD_KEY = "refresh.all.simcard";

    /**
     * 刷新开始时间
     */
    int REFRESH_SIMCARD_START_HOUR = 7;
    String REFRESH_SIMCARD_START_HOUR_KEY = "refresh.simcard.start.hour";

    /**
     * 刷新结束时间
     */
    int REFRESH_SIMCARD_END_HOUR = 22;
    String REFRESH_SIMCARD_END_HOUR_KEY = "refresh.simcard.end.hour";

    /**
     * 刷新分页查询，一页大小
     */
    int REFRESH_SIMCARD_PAGE_SIZE = 200;
    String REFRESH_SIMCARD_PAGE_SIZE_KEY = "refresh.simcard.page.size";

    /**
     * 逗号分隔符
     */
    String SEPARATOR_DOUHAO = ",";


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


    String USER_TYPE_ADMIN = "0";
    String USER_TYPE_DEV = "1";
    String USER_TYPE_IOT = "2";
    String USER_TYPE_DEVANDIOT = "3";


    String USER_TYPE_ADMIN_NAME = "管理员";
    String USER_TYPE_IOT_NAME = "";


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

    /***************************************netty***************************************/
    //ping和pong类型的数据
    byte PING = 1;
    byte PONG = 2;
    //顾客
    byte CUSTOMER = 3;
}