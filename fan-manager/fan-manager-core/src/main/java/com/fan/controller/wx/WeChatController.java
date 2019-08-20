package com.fan.controller.wx;

import com.fan.annotation.ApiVersion;
import com.fan.entity.Msg;
import com.fan.entity.wx.CreateOrderParams;
import com.fan.entity.wx.WxBaseMessage;
import com.fan.remote.wx.WeChatRemote;
import com.fan.service.wxchat.IWxBaseService;
import com.fan.util.FieldConstant;
import com.fan.util.SignUtil;
import com.fan.util.SpringContextUtil;
import com.fan.util.XmlUtil;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author fan
 * @title: WeChatController
 * @projectName fan-share
 * @description: http://m16808311q.iask.in/fan-manager-web/app/wxchat/v1.0/entrance
 * @date
 */
@Slf4j
@ApiVersion
@Api(description = "微信API操作")
@RestController
@RequestMapping("/api/wx/{version}")
public class WeChatController {
    @Value("${wx.token}")
    private String token;
    @Autowired
    private WeChatRemote weChatRemote;
    private Map<String, String> WX_SERVICE_MAP = Maps.newHashMap();

    @PostConstruct
    private void initWxServiceMap() {
        WX_SERVICE_MAP.put(WxBaseMessage.MessageType.text.name(), "wxTextService");
        WX_SERVICE_MAP.put(WxBaseMessage.MessageType.image.name(), "wxImageService");
        WX_SERVICE_MAP.put(WxBaseMessage.MessageType.voice.name(), "wxVoiceMessage");
        WX_SERVICE_MAP.put(WxBaseMessage.MessageType.video.name(), "");
        WX_SERVICE_MAP.put(WxBaseMessage.MessageType.shortvideo.name(), "");
        WX_SERVICE_MAP.put(WxBaseMessage.MessageType.location.name(), "wxLocationService");
        WX_SERVICE_MAP.put(WxBaseMessage.MessageType.link.name(), "");
    }

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
            if (StringUtils.isNotBlank(echostr) && SignUtil.checkWxSignature(signature, timestamp, nonce, token)) {
                System.out.printf("[signature:%s]<-->[timestamp:%s]<-->[nonce: %s]<-->[echostr:%s]%n", signature, timestamp, nonce, echostr);
                response.getOutputStream().print(echostr);
            }
        } catch (Exception e) {
            log.error("wxchat entrance error:{}", e);
        }
    }

    @GetMapping("/getWxAccess_token")
    public Msg getWxAccess_token() {
        try {
            String wxAccess_token = weChatRemote.getWxAccess_token();
            return Msg.success().setDatas(wxAccess_token);
        } catch (Exception e) {
            log.error("getWxAccess_token error:{}", e);
            return Msg.fail();
        }
    }

    @GetMapping("/showWxQrcode")
    public Msg showWxQrcode() {
        try {
            weChatRemote.showWxQrcode();
            return Msg.success();
        } catch (Exception e) {
            log.error("showWxQrcode error:{}", e);
            return Msg.fail();
        }
    }

    /**
     * 微信自动回复
     *
     * @param request
     * @return
     */
    @PostMapping(value = "/entrance", produces = "text/html;charset=UTF-8")
    public String autoReplyMessage(HttpServletRequest request) {
        try {
//          获取微信服务器发送的消息，转换成map对象
            Map<String, String> map = XmlUtil.xmlToMap(request);
            // 获取详细的信息
            // 发送方帐号（open_id）
            String fromUserName = map.get("FromUserName");
            // 公众帐号
            String toUserName = map.get("ToUserName");
            // 消息类型
            String msgType = map.get("MsgType");
            // 消息id
            String msgId = map.get("MsgId");

            //文本消息，消息内容
            String content = map.get("Content");
            //图片和语音消息的mediaId
            String mediaId = map.get("MediaId");

            //定义回复消息对象微信消息的基类 WxBaseMessage
            //set属性值的时候，注意：ToUserName 和 FromUserName的值要反过来!
            WxBaseMessage wxBaseMessage = WxBaseMessage.builder().FromUserName(toUserName).ToUserName(fromUserName).MsgId(Long.parseLong(msgId)).CreateTime(System.currentTimeMillis()).build();
            if(StringUtils.equals(msgType,FieldConstant.WeChat.MESSAGE_TYPE_LOCATION)){
                wxBaseMessage.setMsgType(FieldConstant.WeChat.MESSAGE_TYPE_MUSIC);
            }else{
                wxBaseMessage.setMsgType(msgType);
            }
            //找到对应的服务
            String serviceName = WX_SERVICE_MAP.get(msgType);
            IWxBaseService wxBaseService = SpringContextUtil.getBean(serviceName);
            if (null != wxBaseMessage) {
                switch (msgType) {
                    case FieldConstant.WeChat.MESSAGE_TYPE_TEXT:
                        return wxBaseService.exec(wxBaseMessage, content);
                    case FieldConstant.WeChat.MESSAGE_TYPE_IMAGE:
                        return wxBaseService.exec(wxBaseMessage, mediaId);
                    case FieldConstant.WeChat.MESSAGE_TYPE_VOICE:
                        return wxBaseService.exec(wxBaseMessage, mediaId);
                    case FieldConstant.WeChat.MESSAGE_TYPE_LOCATION:
                        return wxBaseService.exec(wxBaseMessage);
                }
            }
        } catch (Exception e) {
            log.error("wechat receiveMessage error:{}", e);
        }
        return null;
    }

    @PostMapping("/pay")
    @ApiOperation("微信下单")
    public Msg pay(CreateOrderParams createOrderParams) {
        try {
            Msg msg = weChatRemote.pay(createOrderParams);
            return msg;
        } catch (Exception e) {
            log.error("getWxAccess_token error:{}", e);
            return Msg.fail();
        }
    }

    @PostMapping("/payBack")
    @ApiOperation("微信下单回调")
    public Msg payBack() {
        try {
            System.out.println(".........payBack.....coming.......");
            return Msg.success();
        } catch (Exception e) {
            log.error("getWxAccess_token error:{}", e);
            return Msg.fail();
        }
    }
}

