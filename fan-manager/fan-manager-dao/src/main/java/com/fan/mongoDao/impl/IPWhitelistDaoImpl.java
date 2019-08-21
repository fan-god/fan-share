package com.fan.mongoDao.impl;

import com.fan.entity.api.IPWhitelist;
import com.fan.mongoDao.IPWhitelistDao;
import org.springframework.stereotype.Repository;

/**
 * @author fan
 * @title: IPWhitelistDaoImpl
 * @projectName fan-share
 * @description: TODO
 * @date 2019/8/21/002114:22
 */
@Repository
public class IPWhitelistDaoImpl extends GeneralDaoImpl<IPWhitelist> implements IPWhitelistDao{
    @Override
    protected Class<IPWhitelist> getEntityClass() {
        return IPWhitelist.class;
    }
}
