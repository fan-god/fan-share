package com.fan.mongoDao.impl;

import com.fan.entity.DataDictionary;
import com.fan.mongoDao.DictionaryDao;
import org.springframework.stereotype.Repository;

/**
 * @author fan
 * @title: DictionaryDaoImpl
 * @projectName fan-share
 * @description: TODO
 * @date 2019/8/1/000116:34
 */
@Repository
public class DictionaryDaoImpl extends GeneralDaoImpl<DataDictionary> implements DictionaryDao{
    @Override
    protected Class<DataDictionary> getEntityClass() {
        return DataDictionary.class;
    }
}
