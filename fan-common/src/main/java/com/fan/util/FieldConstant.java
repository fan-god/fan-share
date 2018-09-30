package com.fan.util;

/**
 * Created by Achievo on 2018/9/3.
 * 接口字段常量类，存放一些字段名称，便于方便管理，字段出处请写好注释
 */
public interface FieldConstant {

	/*********通用字段名称常量*************/
    //分页的当前页数
    String PAGE = "page";
    //页面显示条数
    String PAGE_SIZE = "page_size";
    //mongodb模糊查询前缀
    String QUERY_PREFIX = "^.*";
    //后缀
    String QUERY_SUFFIX = ".*$";

    /**************缓存通用常量*******************/
    //缓存若为all，则直接清空全部
    String REDIS_ALL = "all";
    //AES密钥获取
    String AES_PASS  = "AESPassWord";
    //用于常用判断数组
    String CONSTANT_ZERO = "0";
    String CONSTANT_ONE = "1";
    String CONSTANT_TWO = "2";
    String CONSTANT_THREE = "3";
    String CONSTANT_FOUR = "4";
    String CONSTANT_FIVE = "5";
    // ，用于分割字符串
    String REDIS_SPLIT=",";
    //redis用户类型缓存前缀
    String URL_AUTHORITY ="userUrlAuthority_";
    //物联卡reids缓存Key
    String IOTSIMCARD_REDIS_KEY = "iotSimCardRedisKey";
    //物联卡redis缓存过期时间
    String IOTSIMCARD_OUTTIME_KEY = "iotSimCard";
    //物联卡reids缓存key
    String STATUS_REDIS_KEY = "statusList_reids_";
    //首页设备总量计数缓存Key
    String IOTSIMCARD_COUNT_KEY = "iotsimcard_count";
    //成功
    String SUCCESS = "success";
    //OK
    String OK = "ok";
    //失败
    String FAIL = "fail";
    String ID = "id";


    
    /*********************************接口通用字段常量*************************************************/
    //操作人，接口用
    String OPERATOR = "operator";
    //名称
    String NAME = "name";
    //ip
    String IP = "ip";
    String PHONE = "phone";
    String EMAIL = "email";
    String TYPE = "type";
    String STATUS = "status";
    //描述
    String DESC = "desc";
    //负责人
    String LEADER = "leader";


    /*******************************产品接口字段名称常量*******************************************/
    String PRODUCT_KEY = "product_key";
    String PRODUCT_ID = "product_id";

    /*********************************保洁接口字段名称常量**************************************/
    //电量小于
    String BATTERY_LT = "battery_lt";
    //电量大于
    String BATTERY_GT = "battery_gt";


    /*********************************门禁设备接口字段名称常量************************************/
    String AREA_ID = "area_id";
    String CENTER_ID = "center_id";
    String PROJECT_ID = "project_id";
    String AREA_NAME = "area_name";
    String CENTER_NAME = "center_name";
    String PROJECT_NAME = "project_name";
    
    /*************************************mq******************************************/
    String MQ_PREFIX = "mq_prefix";
    String CLEAN_WARNING_QUERY_CONDITION = "cleanup_warning_query_condition";
}
