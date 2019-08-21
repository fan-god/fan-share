package com.fan.entity.api;

import com.fan.entity.base.BaseEntity;
import com.fan.entity.base.GeneralBean;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author fan
 * @title: IpWhitelist
 * @projectName fan-share
 * @description: TODO
 * @date 2019/8/21/002114:13
 */
@Document
@Data
@Builder
public class IPWhitelist extends GeneralBean{
    private String ip;
    private String remarks;
    /**
     * 隶属于用户的用户id
     */
    private String userId;
    /**
     * 隶属于用户的用户姓名
     */
    private String username;
}
