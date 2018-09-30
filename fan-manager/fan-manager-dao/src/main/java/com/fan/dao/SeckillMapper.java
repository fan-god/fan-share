package com.fan.dao;


import com.fan.entity.Seckill;
import org.springframework.stereotype.Repository;
@Repository
public interface SeckillMapper {
	int deleteByPrimaryKey(Long id);

	int insert(Seckill record);

	int insertSelective(Seckill record);

	Seckill selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(Seckill record);

	int updateByPrimaryKey(Seckill record);


//	/**
//	 * 减库存
//	 * @param seckillId
//	 * @param killedDate
//	 * @return
//	 */
//	int reduceNumber(long seckillId, Date killedDate);
//
//	/**
//	 * 根据偏移量查询商品列表
//	 * @param offet
//	 * @param limit
//	 * @return
//	 */
//	List<Seckill> queryAll(int offet, int limit);
}