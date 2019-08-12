package com.fan.util;

/**
 * Created by fan on 2018/9/3.
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
    String AES_PASS = "AESPassWord";
    //用于常用判断数组
    String CONSTANT_ZERO = "0";
    String CONSTANT_ONE = "1";
    String CONSTANT_TWO = "2";
    String CONSTANT_THREE = "3";
    String CONSTANT_FOUR = "4";
    String CONSTANT_FIVE = "5";

    /*****************************redis缓存key**********************************/
    String WX_ACCESS_TOKEN_KEY = "wx.access.token.key";
    String WX_TICKET_KEY = "wx.ticket.key";

    /*********************************WeChat****************************************/
    interface WeChat {
        /**
         * 小程序appid
         */
        String appid = "";
        /**
         * 商户号的Appid
         */
        String mch_appid = "";
        /**
         * 商户号
         */
        String mch_id = "";
        /**
         * 回调地址
         */
        String notify_url = "";
        /**
         * 交易类型
         */
        String trade_type = "JSAPI";
        /**
         * 签名类型
         */
        String sign_type = "MD5";
        /**
         * 商户密匙
         */
        String key = "";
        /**
         * 小程序ApiSecret
         */
        String SECRET = "";
        /**
         * 证书地址
         */
        String CERTIFICATE_ADDRESS = "";
        /**
         * 二维码图片地址
         */
        String QRImgRootAddress = "";

        /**
         * 获取access_token
         */
        String GET_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token";
        /**
         * 登录地址
         */
        String LOGIN_URL = "https://api.weixin.qq.com/sns/jscode2session";

        /**
         * 统一下单url
         */
        String CREATE_ORDER_PREFIX_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";

        /**
         * 订单情况查询url
         */
        String ORDER_CHECK_URL = "https://api.mch.weixin.qq.com/pay/orderquery";

        /**
         * 企业付款到零钱
         */
        String COMPANY_TRANSFER_URL = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";

        /**
         * 企业付款查询url
         */
        String TRANSFER_CHECK_URL = "https://api.mch.weixin.qq.com/mmpaymkttransfers/gettransferinfo";

        /**
         * 二维码url
         */
        String QRCODE_URL = "https://api.weixin.qq.com/wxa/getwxacodeunlimit";


        String SEND_TEMPLATE_MSG_URL = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send";
    }

}
