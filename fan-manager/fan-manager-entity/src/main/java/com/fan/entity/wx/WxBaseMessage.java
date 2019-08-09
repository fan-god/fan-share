package com.fan.entity.wx;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author fan
 * @title: WxMessage
 * @projectName fan-share
 * @description: TODO
 * @date 2019/8/7/000716:10
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WxBaseMessage {
    // 开发者微信号
    private String ToUserName;
    // 发送方帐号（一个OpenID）
    private String FromUserName;
    // 消息创建时间 （整型）
    private long CreateTime;
    // 消息类型（text/image/location/link）
    private String MsgType;
    // 消息id，64位整型
    private long MsgId;

    public enum MessageType {
        text, image, voice, video, shortvideo, location, link
    }
}