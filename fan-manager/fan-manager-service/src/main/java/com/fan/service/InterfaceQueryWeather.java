package com.fan.service;


import com.fan.dao.BaseDao;

public class InterfaceQueryWeather implements BaseDao {

	@Override
	public String exec() {
//		System.out.println("今日天气：晴");
		return "今日天气：晴";
	}

}
