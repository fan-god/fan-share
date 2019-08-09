package com.fan.entity.wx;

import lombok.Data;

import java.util.List;

/**
 * @author fan
 * @title: VoicMeaage
 * @projectName fan-share
 * @description: TODO
 * @date 2019/8/9/000914:47
 */
@Data
public class VoiceMessage extends WxBaseMessage{
    private List<MediaId> Voice;
}
