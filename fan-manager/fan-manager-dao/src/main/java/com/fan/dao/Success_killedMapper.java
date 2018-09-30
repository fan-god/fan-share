package com.fan.dao;


import com.fan.entity.Success_killed;
import org.springframework.stereotype.Repository;

@Repository
public interface Success_killedMapper {
	int deleteByPrimaryKey(Success_killed key);

	int insert(Success_killed record);

	/**
	 * 插入购买明细
	 * @param seckilledId
	 * @param userPhone
	 * @return
	 */
	int insertSuccess_killed(long seckilledId, long userPhone);

	int insertSelective(Success_killed record);

	Success_killed selectByPrimaryKey(Success_killed key);

	int updateByPrimaryKeySelective(Success_killed record);

	int updateByPrimaryKey(Success_killed record);

	/**
	 * 利用秒杀的id查询秒杀成功的结果
	 * @param seckilledId
	 * @return
	 */
	Success_killed queryByIdWithSeckill(long seckilledId);
}