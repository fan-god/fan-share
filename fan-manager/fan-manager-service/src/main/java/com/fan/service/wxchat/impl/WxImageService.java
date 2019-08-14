package com.fan.service.wxchat.impl;

import com.fan.entity.wx.ImageMessage;
import com.fan.entity.wx.MediaId;
import com.fan.entity.wx.WxBaseMessage;
import com.fan.service.wxchat.IWxBaseService;
import com.fan.util.ConstantFan;
import com.fan.util.XmlUtil;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author fan
 * @title: WxImageService
 * @projectName fan-share
 * @description: TODO
 * @date 2019/8/9/00099:54
 */
@Service
public class WxImageService implements IWxBaseService{

    @Override
    public String exec(WxBaseMessage wxBaseMessage, String... fields) {
        ImageMessage imageMessage = new ImageMessage();
        BeanUtils.copyProperties(wxBaseMessage, imageMessage);
        List<MediaId> images = Lists.newArrayList();
        images.add(new MediaId(fields[0]));
        imageMessage.setImage(images);
        String respMsg = XmlUtil.beanToXml(imageMessage, ImageMessage.class);
        respMsg = respMsg.replace("<com.fan.entity.wxchat.MediaId>", ConstantFan.EMPTY_STR);
        respMsg = respMsg.replace("</com.fan.entity.wxchat.MediaId>",ConstantFan.EMPTY_STR);
        return respMsg;
    }
}
