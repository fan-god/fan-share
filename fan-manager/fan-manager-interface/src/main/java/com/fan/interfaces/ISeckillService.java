package com.fan.interfaces;

import com.fan.entity.Seckill;
import org.springframework.stereotype.Service;

/**
 * @author:Achievo
 * @Date:2018/9/29.
 * @Description:
 * @PACKAGE_NAME:com.fan.interfaces
 * @PROJECT_NAME:fansys
 */
public interface ISeckillService {
    Seckill selectByPrimaryKey(Long id);
}
