package com.fan.service;


import com.fan.dao.BaseDao;
import org.springframework.stereotype.Service;

@Service
public class InterfaceQueryTel implements BaseDao {

	@Override
	public String exec() {
		return "归属地湖北为武汉";
	}

}
