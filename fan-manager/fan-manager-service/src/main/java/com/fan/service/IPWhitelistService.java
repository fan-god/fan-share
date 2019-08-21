package com.fan.service;

import com.fan.entity.api.IPWhitelist;

/**
 * Created by Administrator on 2019/8/21/0021.
 */
public interface IPWhitelistService {

    Boolean isOnIPWhitelist(String ip);
    void add(IPWhitelist ipWhitelist);
}
