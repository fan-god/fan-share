package com.fan.test.testdata;

import com.fan.dao.SeckillMapper;
import com.fan.dao.UserMapper;
import com.fan.entity.Seckill;
import com.fan.entity.User;
import com.fan.interfaces.ISeckillService;
import com.fan.service.SeckillServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @author:Achievo
 * @Date:2018/9/28.
 * @Description:
 * @PACKAGE_NAME:com.fan.test.testdata
 * @PROJECT_NAME:fansys
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/spring-*.xml"})
public class TestData {
    @Autowired
    private SeckillServiceImpl seckillServiceImpl;

    @Test
    public void test1(){
        Seckill seckill = seckillServiceImpl.selectByPrimaryKey(1000L);
        System.out.println(seckill);
    }

    @Test
    public void test2() {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/spring-*.xml");
        ISeckillService beanA = (ISeckillService) context.getBean("seckillServiceImpl");

        System.out.println(beanA);
    }
}
