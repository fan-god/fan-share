package com.fan.controller;

import com.fan.entity.Msg;
import com.fan.entity.Seckill;
import com.fan.service.SeckillServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author:Achievo
 * @Date:2018/9/29.
 * @Description:
 * @PACKAGE_NAME:com.fan.contrlller
 * @PROJECT_NAME:fansys
 */
@Controller
@RequestMapping("/seckill")
public class SeckillController {

    @Autowired
    private SeckillServiceImpl seckillServiceImpl;

    @RequestMapping("/query")
    @ResponseBody
    public Msg querySeckill() {
        try {
            System.out.println("comming");
            Seckill seckill = seckillServiceImpl.selectByPrimaryKey(1000L);
            return Msg.success().setDatas(seckill);
        } catch (Exception e) {
            return Msg.success().setMsg(e.getMessage());
        }
    }
}
