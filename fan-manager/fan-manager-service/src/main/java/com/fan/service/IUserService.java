package com.fan.service;


import com.fan.entity.ResponseMsg;
import com.fan.entity.User;
import com.fan.service.base.IBaseService;
import com.github.pagehelper.PageInfo;

import java.util.Map;

public interface IUserService{

    int AddGwf1(Map<String, Object> map);

   PageInfo<User> listPageAll(int pageNo, int pageSize, User user);

    Boolean login(User user);

    ResponseMsg register(User user);

    Integer insert(User user);

    User getUserByName(String username);
}
