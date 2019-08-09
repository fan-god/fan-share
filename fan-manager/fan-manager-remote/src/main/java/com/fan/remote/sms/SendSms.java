package com.fan.remote.sms;

import com.fan.util.ConstantFan;
import com.fan.util.HttpCilentUtil;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * @author fan
 * @title: SendSmsUtil
 * @projectName fan-share
 * @description: 发送短信接口
 * @date 2019/7/8/000810:20
 */
@Slf4j
@Component
public class SendSms {
    private  Map<String, String> params = Maps.newHashMap();
    //账号
    @Value("${ms.sms.account}")
    private String account;
    //密码
    @Value("${ms.sms.password}")
    private String password;
    //通讯Key/校验码
    @Value("${ms.sms.tempid}")
    private String tempid;
    //模板编码
    @Value("${ms.sms.veryCode}")
    private String veryCode;
    //请求地址
    @Value("${ms.sms.httpUrl}")
    private String httpUrl;

    enum SmsType {
        MSG_TYPE_1("1"), MSG_TYPE_2("2");
        String value;

        SmsType(String value) {
            this.value = value;
        }
    }

    @PostConstruct
    private Map<String, String> initParams() {
        params.put("username", account);
        params.put("password", password);
        params.put("veryCode", veryCode);
        return params;
    }

    public String sendSms(String mobile, String content) {
        try {
            String sendSmsUrl = httpUrl + "/service/httpService/httpInterface.do?method=sendUtf8Msg";
            params.put("mobile", mobile);
            params.put("content", content);
            params.put("msgtype", SmsType.MSG_TYPE_1.value);
            params.put("code", ConstantFan.CHARSET);
            String result = HttpCilentUtil.doPost(sendSmsUrl, params);
            return result;
        } catch (Exception e) {
            log.error("sendSms error:{}", e);
        }
        return null;
    }

    public String sendTplSms(String mobile, String content) {
        try {
            String sendTplSmsUrl = httpUrl + "/service/httpService/httpInterface.do?method=sendUtf8Msg";
            params.put("mobile", mobile);
            params.put("content", content); //变量值，以英文逗号隔开，测试期间给模板变量的值传递字母、数据或下划线等半角字符才能免审
            params.put("msgtype", SmsType.MSG_TYPE_2.value);     //2-模板短信
            params.put("tempid", tempid);    //模板编号
            params.put("code", ConstantFan.CHARSET);
            String result = HttpCilentUtil.doPost(sendTplSmsUrl, params);
            return result;
        } catch (Exception e) {
            log.error("sendTplSms error:{}", e);
        }
        return null;
    }

    public String getBalance() {
        try {
            String balanceUrl = httpUrl + "/service/httpService/httpInterface.do?method=getAmount";
            String result = HttpCilentUtil.doPost(balanceUrl, params);
            return result;
        } catch (Exception e) {
            log.error("getBalance error:{}", e);
        }
        return null;
    }

    public String queryReport() {
        try {
            String reportUrl = httpUrl + "/service/httpService/httpInterface.do?method=queryReport";
            String result = HttpCilentUtil.doPost(reportUrl, params);
            return result;
        } catch (Exception e) {
            log.error("queryReport error:{}", e);
        }
        return null;
    }

    public String queryMo() {
        try {
            String moUrl = httpUrl + "/service/httpService/httpInterface.do?method=queryMo";
            String result = HttpCilentUtil.doPost(moUrl, params);
            return result;
        } catch (Exception e) {
            log.error("queryMo error:{}", e);
        }
        return null;
    }
}
