package com.fan.service;


import com.fan.entity.User;
import com.fan.service.base.IBaseService;
import com.github.pagehelper.PageInfo;

import java.util.Map;

public interface IUserService extends IBaseService<User>{

    int AddGwf1(Map<String, Object> map);

    PageInfo<User> listPageAll(int pageNo, int pageSize, User user);

    User queryOne(Long id);
}
