package com.fan.dao;


import com.fan.entity.BaseFile;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface BaseFileMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(BaseFile record);

    int insertSelective(BaseFile record);

    BaseFile selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(BaseFile record);

    int updateByPrimaryKey(BaseFile record);
    
    List<BaseFile> getAllFile();
    
    List<BaseFile> getFileByStat(String stat);
}