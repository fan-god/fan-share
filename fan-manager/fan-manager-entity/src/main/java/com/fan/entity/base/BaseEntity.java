package com.fan.entity.base;

import lombok.Data;

import java.util.Date;

/**
 * @author fan
 * @title: BaseEntity
 * @projectName fan-share
 * @description: TODO
 * @date 2019/7/3/00039:26
 */
@Data
public class BaseEntity {
    protected Long id;
    protected Date createDate;
    protected Date updateDate;
    protected String creator;
    protected String modifier;
}
