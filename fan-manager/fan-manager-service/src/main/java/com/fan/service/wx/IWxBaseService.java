package com.fan.service.wx;

import com.fan.entity.wx.WxBaseMessage;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2019/8/9/0009.
 */
public interface IWxBaseService {

    String exec(WxBaseMessage wxBaseMessage, String... fields);

}
