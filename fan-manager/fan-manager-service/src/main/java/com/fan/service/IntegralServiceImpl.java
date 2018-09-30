package com.fan.service;


import com.fan.dao.IntegralMapper;
import com.fan.entity.Integral;
import com.fan.interfaces.IIntegralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IntegralServiceImpl implements IIntegralService {

	@Autowired
	private IntegralMapper integralMapper;
	@Override
	public int deleteByPrimaryKey(Integer id) {
		
		return 0;
	}

	@Override
	public int insert(Integral record) {
		
		return 0;
	}

	@Override
	public int insertSelective(Integral record) {
		
		return 0;
	}

	@Override
	public Integral selectByPrimaryKey(Integer id) {
		
		return null;
	}

	@Override
	public int updateByPrimaryKeySelective(Integral record) {
		
		return 0;
	}

	@Override
	public int updateByPrimaryKey(Integral record) {
		
		return 0;
	}

	@Override
	public Integer getScoreByModu(String modu) {
		return integralMapper.getScoreByModu(modu);
	}

}
