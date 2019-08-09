package com.fan.controller.wx;

import com.fan.annotation.ApiVersion;
import com.fan.entity.Msg;
import com.fan.entity.wx.TextMessage;
import com.fan.entity.wx.WxBaseMessage;
import com.fan.remote.wx.WeChatRemote;
import com.fan.util.SignUtil;
import com.fan.util.XmlUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

/**
 * @author fan
 * @title: WeChatController
 * @projectName fan-share
 * @description: http://m16808311q.iask.in/fan-manager-web/app/wx/v1.0/entrance
 * @date
 */
@Slf4j
@ApiVersion
@RestController
@RequestMapping("/app/wx/{version}")
public class WeChatController {

    @Autowired
    private WeChatRemote weChatRemote;

    /**
     * 绑定微信回调地址
     *
     * @param request
     * @return
     */
    @GetMapping(value = "/entrance")
    public void bindCallbackUrl(HttpServletRequest request, HttpServletResponse response) {
        try {
            String signature = request.getParameter("signature");
            String timestamp = request.getParameter("timestamp");
            String nonce = request.getParameter("nonce");
            String echostr = request.getParameter("echostr");
            if (StringUtils.isNotBlank(echostr) && SignUtil.checkWxSignature(signature, timestamp, nonce)) {
                System.out.printf("[signature:%s]<-->[timestamp:%s]<-->[nonce: %s]<-->[echostr:%s]%n", signature, timestamp, nonce, echostr);
                response.getOutputStream().print(echostr);
            }
        } catch (Exception e) {
            log.error("wx entrance error:{}", e);
        }
    }

    @GetMapping("/getWxAccess_token")
    public Msg getWxAccess_token() {
        String wxAccess_token = weChatRemote.getWxAccess_token();
        return Msg.success().setDatas(wxAccess_token);
    }

    /**
     * 微信自动回复
     * @param request
     * @return
     */
    @PostMapping(value = "/entrance",produces = "text/html;charset=UTF-8")
    public String autoReplyMessage(HttpServletRequest request) {
        try {
//          获取微信服务器发送的消息，转换成map对象
            Map<String, String> map = XmlUtil.parseXmlToMap(request);
            // . 获取详细的信息
            // 发送方帐号（open_id）
            String fromUserName = map.get("FromUserName");
            // 公众帐号
            String toUserName = map.get("ToUserName");
            // 消息类型
            String msgType = map.get("MsgType");
            // 消息内容
            String content = map.get("Content");
            // 消息id
            String msgId = map.get("MsgId");
            //. 定义回复消息对象
            // 也可以用new，然后一个属性一个属性的set
            // 微信消息的基类
            //set属性值的时候，注意：ToUserName 和 FromUserName的值要反过来！是坑!是坑!是坑!
            WxBaseMessage baseMessage = WxBaseMessage.builder().FromUserName(toUserName).ToUserName(fromUserName).MsgType(msgType).MsgId(Long.parseLong(msgId)).CreateTime(new Date().getTime()).build();
            if (WxBaseMessage.MessageType.text.name().equals(msgType)) {//文本消息
                //要回复的消息内容
                String resultContent;
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
                TextMessage textMessage = new TextMessage();
                BeanUtils.copyProperties(baseMessage,textMessage);
                textMessage.setContent(resultContent);
                String respMsg = XmlUtil.beanToXml(textMessage, TextMessage.class);
                return respMsg;
            }
        } catch (Exception e) {
            log.error("wx receiveMessage error:{}", e);
        }
        return null;
    }

}

