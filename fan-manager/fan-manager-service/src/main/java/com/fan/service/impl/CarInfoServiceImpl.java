package com.fan.service.impl;

import com.fan.entity.CarInfo;
import com.fan.mongoDao.CarInfoDao;
import com.fan.service.ICarInfoService;
import com.fan.util.Pagination;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

/**
 * @author fan
 * @title: CarInfoServiceImpl
 * @projectName fan-share
 * @description: TODO
 * @date 2019/7/23/002315:32
 */
@Service
@Slf4j
public class CarInfoServiceImpl implements ICarInfoService {
    @Autowired
    private CarInfoDao carInfoDao;

    public Pagination<CarInfo> findPaginationByQuery(CarInfo carInfo, int pageNo, int pageSize) {
        try {
            return carInfoDao.findPaginationByQuery(null, pageNo, pageSize);
        } catch (Exception e) {
            log.error("{}", e);
        }
        return null;
    }

    public void insert(CarInfo carInfo) {
        try {
            this.carInfoDao.insert(carInfo);
        } catch (Exception e) {
            log.error("{}", e);
        }
    }

    public void update(CarInfo carInfo) {
        try {
            Query query = new Query();
            query.addCriteria(Criteria.where("brandName").is(carInfo.getBrandName()));
            Update update = Update.update("displacement",1.8);
            this.carInfoDao.updateFirst(query,update);
        } catch (Exception e) {
            log.error("{}", e);
        }
    }
}
