package com.fan.util;

import org.apache.http.client.HttpClient;

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
    String WX_ACCESS_TOKEN_KEY = "wxchat.access.token.key";
    String WX_TICKET_KEY = "wxchat.ticket.key";

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
        /**
         * 上传素材地址
         */
        String UPLOAD_MATERIAL_URL = "https://api.weixin.qq.com/cgi-bin/material/add_news";

        String SEND_TEMPLATE_MSG_URL = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send";

        String MESSAGE_TYPE_TEXT = "text";
        String MESSAGE_TYPE_IMAGE = "image";
        String MESSAGE_TYPE_VOICE = "voice";
        String MESSAGE_TYPE_VIDEO = "video";
        String MESSAGE_TYPE_SHORTVIDEO = "shortvideo";
        String MESSAGE_TYPE_LOCATION = "location";
        String MESSAGE_TYPE_LINK = "link";
        String MESSAGE_TYPE_MUSIC = "music";
    }

    /*************************************wxpay**********************************/
    interface WxPay {
        String DOMAIN_API = "api.mch.weixin.qq.com";
        String DOMAIN_API2 = "api2.mch.weixin.qq.com";
        String DOMAIN_APIHK = "apihk.mch.weixin.qq.com";
        String DOMAIN_APIUS = "apius.mch.weixin.qq.com";


        String FIELD_SIGN = "sign";
        String FIELD_SIGN_TYPE = "sign_type";

        String WXPAYSDK_VERSION = "WXPaySDK/3.0.9";
        String USER_AGENT = WXPAYSDK_VERSION +
                " (" + System.getProperty("os.arch") + " " + System.getProperty("os.name") + " " + System.getProperty("os.version") +
                ") Java/" + System.getProperty("java.version") + " HttpClient/" + HttpClient.class.getPackage().getImplementationVersion();

        String MICROPAY_URL_SUFFIX = "/pay/micropay";
        String UNIFIEDORDER_URL_SUFFIX = "/pay/unifiedorder";
        String ORDERQUERY_URL_SUFFIX = "/pay/orderquery";
        String REVERSE_URL_SUFFIX = "/secapi/pay/reverse";
        String CLOSEORDER_URL_SUFFIX = "/pay/closeorder";
        String REFUND_URL_SUFFIX = "/secapi/pay/refund";
        String REFUNDQUERY_URL_SUFFIX = "/pay/refundquery";
        String DOWNLOADBILL_URL_SUFFIX = "/pay/downloadbill";
        String REPORT_URL_SUFFIX = "/payitil/report";
        String SHORTURL_URL_SUFFIX = "/tools/shorturl";
        String AUTHCODETOOPENID_URL_SUFFIX = "/tools/authcodetoopenid";

        // sandbox
        String SANDBOX_MICROPAY_URL_SUFFIX = "/sandboxnew/pay/micropay";
        String SANDBOX_UNIFIEDORDER_URL_SUFFIX = "/sandboxnew/pay/unifiedorder";
        String SANDBOX_ORDERQUERY_URL_SUFFIX = "/sandboxnew/pay/orderquery";
        String SANDBOX_REVERSE_URL_SUFFIX = "/sandboxnew/secapi/pay/reverse";
        String SANDBOX_CLOSEORDER_URL_SUFFIX = "/sandboxnew/pay/closeorder";
        String SANDBOX_REFUND_URL_SUFFIX = "/sandboxnew/secapi/pay/refund";
        String SANDBOX_REFUNDQUERY_URL_SUFFIX = "/sandboxnew/pay/refundquery";
        String SANDBOX_DOWNLOADBILL_URL_SUFFIX = "/sandboxnew/pay/downloadbill";
        String SANDBOX_REPORT_URL_SUFFIX = "/sandboxnew/payitil/report";
        String SANDBOX_SHORTURL_URL_SUFFIX = "/sandboxnew/tools/shorturl";
        String SANDBOX_AUTHCODETOOPENID_URL_SUFFIX = "/sandboxnew/tools/authcodetoopenid";
    }

    /*******************************阿里短信服务***************************************/
    interface AliSms{
        String AccessKeyId = "";
        String AccessKeySecret = "";
        String Action = "SendSms";
        String SignName1 = "";
        String SignName2 = "";
        String RegionId = "default";
        String VERSION = "2017-05-25";
        String DOMAIN = "dysmsapi.aliyuncs.com";
    }
}
