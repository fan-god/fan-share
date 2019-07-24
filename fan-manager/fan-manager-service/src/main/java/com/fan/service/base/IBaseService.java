package com.fan.service.base;

import com.fan.entity.base.BaseEntity;

import java.util.List;

/**
 * @author fan
 * @title: IBaseService
 * @projectName fan-share
 * @description: TODO
 * @date 2019/7/15/001510:12
 */
public interface IBaseService<T extends BaseEntity> {
    int deleteByPrimaryKey(Long userid);

    int insert(T t);

    int insertSelective(T t);

    T selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(T t);

    int updateByPrimaryKey(T t);

    List<T> listAll(T t);

    Integer isExist(String uniqueField);
}
