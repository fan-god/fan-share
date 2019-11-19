package com.fan.dao;

import com.fan.base.BaseMapper;
import com.fan.entity.User;
import com.fan.annotation.DataSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
@DataSource("dataSource_first")
public interface UserMapper extends BaseMapper<User>{
    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    Integer login(User record);

    User getUserByName(String username);

    String getRoleByName(String username);

    Integer deleteUserById(Long id);
}