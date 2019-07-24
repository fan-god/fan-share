package com.fan.mongoDao.impl;

import com.fan.mongoDao.CarInfoDao;
import com.fan.entity.CarInfo;
import org.springframework.stereotype.Repository;

/**
 * @author fan
 * @title: CarInfoDaoImpl
 * @projectName fan-share
 * @description: TODO
 * @date 2019/7/23/002315:28
 */
@Repository
public class CarInfoDaoImpl extends GeneralDaoImpl<CarInfo> implements CarInfoDao {

    @Override
    protected Class<CarInfo> getEntityClass() {
        return CarInfo.class;
    }
}
