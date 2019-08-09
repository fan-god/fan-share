package com.fan.entity.wx;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author fan
 * @title: WxImages
 * @projectName fan-share
 * @description: TODO
 * @date 2019/8/9/000911:42
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MediaId {
    private String MediaId;
}
