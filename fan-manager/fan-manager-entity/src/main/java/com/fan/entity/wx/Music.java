package com.fan.entity.wx;

import lombok.Data;

/**
 * @author fan
 * @title: Musci
 * @projectName fan-share
 * @description: TODO
 * @date 2019/8/15/001514:34
 */
@Data
public class Music {
    private String Title;
    private String Description;
    private String MusicUrl;
    private String HQMusicUrl;
    private String ThumbMediaId;
}
