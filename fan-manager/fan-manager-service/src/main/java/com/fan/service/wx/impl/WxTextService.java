package com.fan.service.wx.impl;

import com.fan.entity.wx.TextMessage;
import com.fan.entity.wx.WxBaseMessage;
import com.fan.service.wx.IWxBaseService;
import com.fan.util.XmlUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * @author fan
 * @title: WxTextService
 * @projectName fan-share
 * @description: TODO
 * @date 2019/8/9/00099:37
 */
@Service
public class WxTextService implements IWxBaseService {
    @Override
    public String exec(WxBaseMessage wxBaseMessage, String... fields) {
        String content = fields[0];
        String resultContent;
        if(StringUtils.isBlank(content)){
            resultContent = "caonima";
        }else{
            if ("童俊".equals(content)) {
                resultContent = "童俊是傻逼";
            } else if ("美女".equalsIgnoreCase(content)) {
                resultContent = "<a href='https://image.baidu.com/search/index?tn=baiduimage&ct=201326592&lm=-1&cl=2&ie=gb18030&word=%C3%C0%C5%AE%CD%BC%C6%AC&fr=ala&ala=1&alatpl=adress&pos=0&hs=2&xthttps=111111'>美女</a>";
            } else if ("俊儿".equals(content) || "JAVA".equals(content)) {
                resultContent = "童俊草尼玛";
            } else if ("tongjun".equals(content) || "javascript".equals(content)) {
                resultContent = "童俊是狗";
            } else {
                resultContent = "发尼玛的什么鸡儿玩意：" + content;
            }
        }
        TextMessage textMessage = new TextMessage();
        BeanUtils.copyProperties(wxBaseMessage, textMessage);
        textMessage.setContent(resultContent);
        String respMsg = XmlUtil.beanToXml(textMessage, TextMessage.class);
        return respMsg;
    }
}
