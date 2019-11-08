package com.fan.service.impl;

import com.fan.base.BaseMapper;
import com.fan.entity.base.BaseEntity;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author fan
 * @title: BaseServiceImpl
 * @projectName fan-share
 * @description: TODO
 * @date 2019/7/3/00039:53
 */
public abstract class BaseServiceImpl<T extends BaseEntity> {

    protected PageInfo<T> listPageAll(Integer pageNo, Integer pageSize, T t, BaseMapper baseMapper) {
        if (null == pageNo) {
            pageNo = 1;
        }
        if (null == pageSize) {
            pageSize = 10;
        }
        PageHelper.startPage(pageNo, pageSize);
        List<T> list = baseMapper.listAll(t);
        PageInfo<T> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }
}
