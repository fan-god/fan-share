package com.fan.service;

import com.fan.entity.CarInfo;
import com.fan.util.Pagination;

/**
 * @author fan
 * @title: ICarInfoService
 * @projectName fan-share
 * @description: TODO
 * @date 2019/7/23/002315:31
 */
public interface ICarInfoService {
    Pagination<CarInfo> findPaginationByQuery(CarInfo carInfo, int pageNo, int pageSize);
    void insert(CarInfo carInfo);
    void update(CarInfo carInfo);
}
