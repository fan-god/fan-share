package com.fan.test;

import com.fan.entity.CarInfo;
import com.fan.entity.User;
import com.fan.remote.SendSms;
import com.fan.service.ICarInfoService;
import com.fan.service.ISeckillService;
import com.fan.service.IUserService;
import com.fan.util.ConstantFan;
import com.fan.util.DataConvertUtil;
import com.fan.util.Pagination;
import com.fan.util.RedisUtil;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * @author:fan
 * @Date:2018/9/28.
 * @Description:
 * @PACKAGE_NAME:com.fan.test.testdata
 * @PROJECT_NAME:fansys
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:spring/spring-*.xml"})
public class TestData {
    @Autowired
    private ISeckillService seckillService;
    @Autowired
    private IUserService userService;
    @Autowired
    private RedisUtil redis;
    @Autowired
    private SendSms sendSms;
    @Autowired
    private ICarInfoService carInfoService;

    @Test
    public void test1(){
        User user = new User();
        PageInfo<User> pageInfo = userService.listPageAll(1, 20, user);
        System.out.println(DataConvertUtil.beanToString(pageInfo, ConstantFan.JSON));
    }

    @Test
    public void test2() {
        System.out.println(redis.getValue("vtykkp",10));
    }

    @Test
    public void test3(){
        String s_sms = sendSms.sendTplSms("18627054863", "@1@=Zhang_Chuan_Pu");
//        String s_report = sendSmsUtil.queryReport();
//        String s_balance = sendSmsUtil.getBalance();
//        String s_mo = sendSmsUtil.queryMo();
        System.out.printf("================>%s",s_sms);
    }

    @Test
    public void test4(){
        try {
            CarInfo carInfo = CarInfo.builder().brandName("五菱宏光").color("白色").displacement(2.0).build();
            carInfoService.insert(carInfo);
            Pagination<CarInfo> pagination = carInfoService.findPaginationByQuery(null, 1, 2);
            System.out.println(pagination);
        } catch (Exception e) {
          log.error("test4 error:{}",e);
        }
    }

    @Test
    public void test5(){
        try {
            CarInfo carInfo = CarInfo.builder().brandName("五菱宏光").build();
            carInfoService.update(carInfo);
        } catch (Exception e) {
            log.error("test4 error:{}",e);
        }
    }
}
