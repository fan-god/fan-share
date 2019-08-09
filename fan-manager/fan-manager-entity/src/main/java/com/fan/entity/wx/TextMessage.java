package com.fan.entity.wx;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * @author fan
 * @title: TextMessage
 * @projectName fan-share
 * @description: TODO
 * @date 2019/8/7/000716:12
 */
@Data
@NoArgsConstructor
public class TextMessage extends WxBaseMessage {

    private String Content;// 文本消息内容

}
