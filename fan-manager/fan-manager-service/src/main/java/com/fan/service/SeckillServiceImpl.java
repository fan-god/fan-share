package com.fan.service;

import com.fan.dao.SeckillMapper;
import com.fan.entity.Seckill;
import com.fan.interfaces.ISeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author:Achievo
 * @Date:2018/9/29.
 * @Description:
 * @PACKAGE_NAME:com.fan.service
 * @PROJECT_NAME:fansys
 */
@Service("seckillServiceImpl")
public class SeckillServiceImpl implements ISeckillService {
    @Autowired
    private SeckillMapper seckillMapper;
    @Override
    public Seckill selectByPrimaryKey(Long id) {
        return seckillMapper.selectByPrimaryKey(id);
    }
}
