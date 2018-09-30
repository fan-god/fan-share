package com.fan.service;


import com.fan.dao.UserMapper;
import com.fan.entity.User;
import com.fan.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private UserMapper um;
	@Override
	public List<User> getAllUser() {
		return um.getAllUser();
	}
	@Override
	public int deleteByPrimaryKey(Integer userid) {
		
		return 0;
	}
	@Override
	public int insert(User record) {
		return um.insert(record);
	}
	@Override
	public int insertSelective(User record) {
		
		return 0;
	}
	@Override
	public User selectByPrimaryKey(Integer userid) {
		
		return null;
	}
	@Override
	public int updateByPrimaryKeySelective(User record) {
		
		return 0;
	}
	@Override
	public int updateByPrimaryKey(User record) {
		
		return 0;
	}
	@Override
	public int AddGwf1(Map<String,Object> map) {
		return um.AddGwf1(map);
	}

}
