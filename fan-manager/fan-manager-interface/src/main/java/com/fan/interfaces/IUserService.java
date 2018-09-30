package com.fan.interfaces;


import com.fan.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
public interface IUserService {

    int deleteByPrimaryKey(Integer userid);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer userid);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
	List<User> getAllUser();
	
	int AddGwf1(Map<String, Object> map);
}
