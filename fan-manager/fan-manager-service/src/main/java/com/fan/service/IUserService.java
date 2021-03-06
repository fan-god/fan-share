package com.fan.service;


import com.fan.entity.ResponseMsg;
import com.fan.entity.User;
import com.fan.service.base.IBaseService;
import com.github.pagehelper.PageInfo;

import java.util.Map;

public interface IUserService {

    int AddGwf1(Map<String, Object> map);

    PageInfo<User> listPageAll(Integer pageNo, Integer pageSize, User user);

    User queryUserById(Long id);

    Boolean login(User user);

    ResponseMsg register(User user);

    Integer insert(User user);

    User getUserByName(String username);

    String getRoleByName(String username);

    Integer updateUser(User user);

    Integer deleteUserById(Long id);
}
