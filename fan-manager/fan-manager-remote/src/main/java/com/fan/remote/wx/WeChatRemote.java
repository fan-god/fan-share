package com.fan.remote.wx;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fan.util.ConstantFan;
import com.fan.util.FieldConstant;
import com.fan.util.HttpCilentUtil;
import com.fan.util.RedisUtil;
import com.google.common.collect.Maps;
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
@Component
public class WeChatRemote {
    @Autowired
    private RedisUtil redisUtil;
    @Value("${wx.appId}")
    private String appid;
    @Value("${wx.appSecret}")
    private String secret;
    @Value("${wx.url}")
    private String url;
    @Value("${wx.grant_type}")
    private String grant_type;

    public String getWxAccess_token() {
        String access_token = redisUtil.getValue(FieldConstant.WX_ACCESS_TOKEN_KEY, ConstantFan.DBINDEX_3);
        if(StringUtils.isBlank(access_token)){
            Map<String, String> params = Maps.newHashMap();
            params.put("appid", appid);
            params.put("secret", secret);
            params.put("grant_type", grant_type);
            String result = HttpCilentUtil.doGet(url, params, null);
            if(StringUtils.isBlank(result)) {
                throw new RuntimeException("获取会话失败");
            }
            JSONObject json = JSON.parseObject(result);
            access_token = json.getString("access_token");
            if(StringUtils.isNotBlank(access_token)){
                Long expires_in = json.getLong("expires_in");
                redisUtil.addValueTime(FieldConstant.WX_ACCESS_TOKEN_KEY,access_token,expires_in, ConstantFan.DBINDEX_3);
            }
        }
        return access_token;
    }
}
