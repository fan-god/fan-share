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
            if ("python".equals(content)) {
                resultContent = "人生苦短，我用python";
            } else if ("php".equals(content) || "PHP".equals(content)) {
                resultContent = "PHP是世界上最好的语言";
            } else if ("java".equals(content) || "JAVA".equals(content)) {
                resultContent = "JAVA太特么复杂了";
            } else if ("js".equals(content) || "javascript".equals(content)) {
                resultContent = "老子是脚本！跟java没半毛钱关系！";
            } else {
                resultContent = "您的开发语言是：" + content;
            }
        }
        TextMessage textMessage = new TextMessage();
        BeanUtils.copyProperties(wxBaseMessage, textMessage);
        textMessage.setContent(resultContent);
        String respMsg = XmlUtil.beanToXml(textMessage, TextMessage.class);
        return respMsg;
    }
}
