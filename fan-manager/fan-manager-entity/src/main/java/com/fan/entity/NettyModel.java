package com.fan.entity;

import lombok.Data;

import java.io.Serializable;
import org.msgpack.annotation.Message;

/**
 * @author fan
 * @title: NettyModel
 * @projectName fan-share
 * @description: TODO
 * @date 2019/7/17/001714:56
 */
@Data
@Message
public class NettyModel implements Serializable{

    private static final long serialVersionUID = 1L;

    //类型
    private int type;

    //内容
    private String body;

}
