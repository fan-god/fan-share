package com.fan.entity.wx;

import lombok.Data;

/**
 * @author fan
 * @title: MusicMessage
 * @projectName fan-share
 * @description: TODO
 * @date 2019/8/15/001514:14
 */
@Data
public class MusicMessage extends WxBaseMessage{
    private Music Music;
}
