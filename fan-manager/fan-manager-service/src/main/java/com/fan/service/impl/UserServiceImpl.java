package com.fan.service.impl;


import com.fan.dao.UserMapper;
import com.fan.entity.ResponseMsg;
import com.fan.entity.User;
import com.fan.service.IUserService;
import com.fan.util.SpringContextUtil;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

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
    public ResponseMsg register(User user) {
        String username = user.getUsername();
        if (null == userMapper.isExist(username)) {
            if (userMapper.insertSelective(user) > 0) {
                //发布用户注册事件
                SpringContextUtil.getApplicationContext().publishEvent(new UserRegisterEvent(this, user));
                return ResponseMsg.success();
            }
        } else {
            return ResponseMsg.fail().setMsg("用户已存在");
        }
        return ResponseMsg.fail();
    }

    @Override
    public Integer insert(User user) {
        return userMapper.insertSelective(user);
    }
}
