package com.fan.service;

import com.fan.entity.DataDictionary;

/**
 * Created by Administrator on 2019/8/1/0001.
 */
public interface IDictionaryService {
    String getDicValueByKey(String dicKey);

    void addDic(DataDictionary dic);

    Boolean isExist(String dicKey);

    void deleteDic(DataDictionary dic);
}
