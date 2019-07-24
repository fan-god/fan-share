package com.fan.entity;

import com.fan.entity.base.GeneralBean;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author fan
 * @title: car
 * @projectName fan-share
 * @description: TODO
 * @date 2019/7/23/002315:11
 */
@Document
@Data
@Builder
public class CarInfo extends GeneralBean {

    private String color;
    private String brandName;
    private Double displacement;

}
