package com.fan.base;

import com.fan.entity.base.BaseEntity;

import java.util.List;

/**
 * Created by fan on 2019/7/3/0003.
 */
public interface BaseMapper<T extends BaseEntity> {

    int deleteByPrimaryKey(Long userid);

    int insert(T t);

    int insertSelective(T t);

    T selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(T t);

    int updateByPrimaryKey(T t);

    List<T> listAll(T t);

    Integer isExist(String uniqueField);

}
