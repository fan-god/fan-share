package com.fan.controller.v1;

import com.fan.entity.Msg;
import com.fan.entity.Seckill;
import com.fan.service.ISeckillService;
import com.fan.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author:fan
 * @Date:2018/9/29.
 * @Description:
 * @PACKAGE_NAME:com.fan.contrlller
 * @PROJECT_NAME:fansys
 */
@RestController
@RequestMapping("/api/seckill")
@Slf4j
public class SeckillController {
    @Autowired
    private ISeckillService seckillService;
    @Autowired
    private RedisUtil redis;

    @RequestMapping("/query")
    public Msg querySeckill() {
        try {
            if(redis.addValue("vtykkp","你有弄窘况",10)){
                System.out.println(123);
            }
            Seckill seckill = seckillService.selectByPrimaryKey(1000L);
            return Msg.success();
        } catch (Exception e) {
            log.error("SeckillController error:{}",e);
        }
        return Msg.fail();
    }
}
