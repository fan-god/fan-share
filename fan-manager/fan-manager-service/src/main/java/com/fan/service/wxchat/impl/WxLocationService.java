package com.fan.service.wxchat.impl;

import com.fan.entity.wx.Music;
import com.fan.entity.wx.MusicMessage;
import com.fan.entity.wx.WxBaseMessage;
import com.fan.service.wxchat.IWxBaseService;
import com.fan.util.ConstantFan;
import com.fan.util.XmlUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;

/**
 * @author fan
 * @title: WxLocationService
 * @projectName fan-share
 * @description: TODO
 * @date 2019/8/15/001514:12
 */
@Service
@Slf4j
public class WxLocationService implements IWxBaseService {
    @Override
    public String exec(WxBaseMessage wxBaseMessage, String... fields) throws InvocationTargetException, IllegalAccessException {
        MusicMessage musicMessage = new MusicMessage();
        BeanUtils.copyProperties(wxBaseMessage, musicMessage);
        Music music = new Music();
        music.setDescription("测试");
        music.setMusicUrl("https://music.163.com/#/song?id=512613695");
        music.setHQMusicUrl("https://music.163.com/#/song?id=512613695");
        music.setTitle("回复音乐测试");
        music.setThumbMediaId("MzI4ODQ3NTc0OV8xMDAwMDAwOTU");
        musicMessage.setMusic(music);
        String respMsg = XmlUtil.beanToXml(musicMessage, MusicMessage.class);
        return respMsg;
    }
}
