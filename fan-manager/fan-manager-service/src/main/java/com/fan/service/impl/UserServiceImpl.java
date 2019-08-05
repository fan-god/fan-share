package com.fan.service.impl;


import com.fan.dao.UserMapper;
import com.fan.entity.User;
import com.fan.service.IUserService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public int AddGwf1(Map<String, Object> map) {
        return 1;
    }

    @Override
    public PageInfo<User> listPageAll(int pageNo, int pageSize, User user) {
        return listPageAll(pageNo, pageSize, user, userMapper);
    }

    @Override
    public Boolean login(User user) {
        return null != userMapper.login(user);
    }

    @Override
    public int deleteByPrimaryKey(Long userid) {
        return 0;
    }

    @Override
    public int insert(User user) {
        return userMapper.insert(user);
    }

    @Override
    public int insertSelective(User user) {
        return 0;
    }

    @Override
    public User selectByPrimaryKey(Long id) {
        return null;
    }

    @Override
    public int updateByPrimaryKeySelective(User user) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(User user) {
        return 0;
    }

    @Override
    public List<User> listAll(User user) {
        return null;
    }

    @Override
    public Integer isExist(String uniqueField) {
        return null;
    }
}
