package com.fan.service.impl;

import com.fan.entity.api.IPWhitelist;
import com.fan.mongoDao.IPWhitelistDao;
import com.fan.service.IPWhitelistService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;


/**
 * @author fan
 * @title: IPWhitelistServiceImpl
 * @projectName fan-share
 * @description: TODO
 * @date 2019/8/21/002114:25
 */
@Service
@Slf4j
public class IPWhitelistServiceImpl implements IPWhitelistService {
    @Autowired
    private IPWhitelistDao ipWhitelistDao;

    /**
     * 查看ip是否在ip白名单
     *
     * @param ip
     * @return
     */
    public Boolean isOnIPWhitelist(String ip) {
        try {
            Query query = new Query();
            query.addCriteria(Criteria.where("ip").is(ip));
            IPWhitelist ipWhitelist = ipWhitelistDao.findOneByQuery(query);
            return ipWhitelist != null;
        } catch (Exception e) {
            log.error("{}", e);
            return false;
        }
    }

    /**
     * 查看ip是否在ip白名单
     *
     * @param ipWhitelist
     * @return
     */
    public void add(IPWhitelist ipWhitelist) {
        try {
            ipWhitelistDao.save(ipWhitelist);
        } catch (Exception e) {
            log.error("{}", e);
        }
    }
}
