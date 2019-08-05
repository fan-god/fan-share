package com.fan.service.impl;

import com.fan.entity.DataDictionary;
import com.fan.mongoDao.DictionaryDao;
import com.fan.service.IDictionaryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

/**
 * @author fan
 * @title: DictionaryServiceImpl
 * @projectName fan-share
 * @description: TODO
 * @date 2019/8/1/000116:36
 */
@Slf4j
@Service
public class DictionaryServiceImpl implements IDictionaryService {

    @Autowired
    private DictionaryDao dictionaryDao;

    public String getDicValueByKey(String dicKey) {
        try {
            Query query = new Query();
            query.addCriteria(Criteria.where("dicKey").is(dicKey));
            DataDictionary dic = dictionaryDao.findOneByQuery(query);
            if (null != dic) {
                return dic.getDicValue();
            }
        } catch (Exception e) {
            log.error("getDicValueByKey error:{}", e);
        }
        return null;
    }

    public void addDic(DataDictionary dic) {
        try {
            if (!this.isExist(dic.getDicKey())) {
                dictionaryDao.save(dic);
            }
        } catch (Exception e) {
            log.error("getDicValueByKey error:{}", e);
        }
    }

    public Boolean isExist(String dicKey) {
        try {
            return null != this.getDicValueByKey(dicKey);
        } catch (Exception e) {
            log.error("getDicValueByKey error:{}", e);
            return false;
        }
    }

    public void deleteDic(DataDictionary dic) {
        try {
            dictionaryDao.remove(dic);
        } catch (Exception e) {
            log.error("getDicValueByKey error:{}", e);
        }
    }
}
