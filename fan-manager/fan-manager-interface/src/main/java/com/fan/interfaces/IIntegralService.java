package com.fan.interfaces;


import com.fan.entity.Integral;
import org.springframework.stereotype.Service;

public interface IIntegralService {

	int deleteByPrimaryKey(Integer id);

	int insert(Integral record);

	int insertSelective(Integral record);

	Integral selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(Integral record);

	int updateByPrimaryKey(Integral record);

	Integer getScoreByModu(String modu);
}
