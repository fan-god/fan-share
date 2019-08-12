package com.fan.remote.wx;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fan.entity.Msg;
import com.fan.entity.wx.CreateOrderParams;
import com.fan.util.*;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author fan
 * @title: WeChatUserInfo
 * @projectName fan-share
 * @description: TODO
 * @date 2019/8/7/000715:01
 */
@Slf4j
@Component
public class WeChatRemote {
    @Autowired
    private RedisUtil redisUtil;
    @Value("${wx.appId}")
    private String appid;
    @Value("${wx.appSecret}")
    private String secret;
    @Value("${wx.grant_type}")
    private String grant_type;

    /**
     * 获取access_token
     *
     * @return
     */
    public String getWxAccess_token() {
        String access_token = redisUtil.getValue(FieldConstant.WX_ACCESS_TOKEN_KEY, ConstantFan.DBINDEX_3);
        if (StringUtils.isBlank(access_token)) {
            Map<String, String> params = Maps.newHashMap();
            params.put("appid", appid);
            params.put("secret", secret);
            params.put("grant_type", grant_type);
            String result = HttpCilentUtil.doGet(FieldConstant.WeChat.GET_ACCESS_TOKEN_URL, params, null);
            if (StringUtils.isBlank(result)) {
                throw new RuntimeException("获取会话失败");
            }
            JSONObject json = JSON.parseObject(result);
            access_token = json.getString("access_token");
            if (StringUtils.isNotBlank(access_token)) {
                Long expires_in = json.getLong("expires_in");
                redisUtil.addValueTime(FieldConstant.WX_ACCESS_TOKEN_KEY, access_token, expires_in, ConstantFan.DBINDEX_3);
            }
        }
        return access_token;
    }

    /**
     * 获取二维码
     *
     * @return
     */
    public String getWxQrcode() {
        String ticket = redisUtil.getValue(FieldConstant.WX_TICKET_KEY, ConstantFan.DBINDEX_3);
        if (StringUtils.isBlank(ticket)) {
            Map<String, String> params = Maps.newHashMap();
            params.put("access_token", this.getWxAccess_token());
            JSONObject jsonBody = JSON.parseObject(ConstantFan.EMPTY_JSON);
            jsonBody.put("action_name", "QR_LIMIT_STR_SCENE");
            JSONObject childJson = JSON.parseObject("{\"scene\": {\"scene_str\": \"test\"}}");
            jsonBody.put("action_info", childJson);
            System.out.println(jsonBody.toJSONString());

            String result = HttpCilentUtil.doPost("https://api.weixin.qq.com/cgi-bin/qrcode/create", params, jsonBody.toJSONString());
            JSONObject json = JSON.parseObject(result);
            ticket = json.getString("ticket");
//            String url = json.getString("url");
            Long expire_seconds = json.getLong("expire_seconds");
            redisUtil.addValueTime(FieldConstant.WX_TICKET_KEY, ticket, expire_seconds, ConstantFan.DBINDEX_3);
        }
        return ticket;
    }

    /**
     * 展示二维码
     *
     * @return
     */
    public void showWxQrcode() {
        HttpCilentUtil.doGet("https://api.weixin.qq.com/cgi-bin/qrcode/create", this.getWxQrcode());
    }


    /**
     * 统一下单
     *
     * @param orderParams
     * @param resultParse
     * @return
     */
    public Msg pay(CreateOrderParams orderParams, WeChatResultParseAbstract resultParse){
        //解析参数
        String urlParam = WeChatUtil.concatOrderParams(orderParams);//参数按字典顺序连接起来
        String sign = SignUtil.getMD5(urlParam);//MD5加密形成签名sign，官方文档固定格式
        orderParams.setSign(sign);//将生成的签名放入
        String xmlStr = XmlUtil.beanToXml(orderParams);//转为xml

        log.info("微信下单参数转换为xml:{}", xmlStr);

        resultParse.setUrl(FieldConstant.WeChat.CREATE_ORDER_PREFIX_URL);
        resultParse.setXmlStr(xmlStr);
        resultParse.setApiDesc("<< 统一下单 >>");

        return resultParse.ResultParse();
    }
}
