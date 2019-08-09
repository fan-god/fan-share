package com.fan.entity.wx;

import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * @author fan
 * @title: ImageMessage
 * @projectName fan-share
 * @description: TODO
 * @date 2019/8/8/000816:55
 */
@Data
@XmlRootElement(name = "xml")
public class ImageMessage extends WxBaseMessage {
    private List<MediaId> Image;
}
