package com.fan.test;

import com.fan.entity.CarInfo;
import com.fan.entity.User;
import com.fan.remote.sms.SendSms;
import com.fan.remote.wx.WeChatRemote;
import com.fan.service.ICarInfoService;
import com.fan.service.IDictionaryService;
import com.fan.service.ISeckillService;
import com.fan.service.IUserService;
import com.fan.util.ConstantFan;
import com.fan.util.DataConvertUtil;
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
    @Autowired
    private IDictionaryService dictionaryService;
    @Autowired
    private WeChatRemote weChatRemote;

    @Test
    public void test1() {
        User user = new User();
        PageInfo<User> pageInfo = userService.listPageAll(1, 20, user);
        System.out.println(DataConvertUtil.beanToString(pageInfo, ConstantFan.JSON));
    }

    @Test
    public void test2() {
        try {
            String str = redis.getHashValue("spring:session:sessions:ea1be81a-3df7-42d8-babf-03f65e828e3f", "sessionAttr:user", 0);
            String s = new String(str.getBytes(ConstantFan.CHARSET));
            System.out.println(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println();
    }

    @Test
    public void test3() {
        String s_sms = sendSms.sendTplSms("18627054863", "@1@=Zhang_Chuan_Pu");
//        String s_report = sendSmsUtil.queryReport();
//        String s_balance = sendSmsUtil.getBalance();
//        String s_mo = sendSmsUtil.queryMo();
        System.out.printf("================>%s", s_sms);
    }

    @Test
    public void test4() {
        try {
//            DataDictionary dic = DataDictionary.builder().dicKey("ftvybyubn").dicValue("drcybgun").build();
//           dictionaryService.addDic(dic);
            String value = dictionaryService.getDicValueByKey("ftvybyubn");
            System.out.println(value);
        } catch (Exception e) {
            log.error("test4 error:{}", e);
        }
    }

    @Test
    public void test5() {
        try {
            CarInfo carInfo = CarInfo.builder().brandName("五菱宏光").build();
            carInfoService.update(carInfo);
        } catch (Exception e) {
            log.error("test4 error:{}", e);
        }
    }

    @Test
    public void test6() {
        try {
            String wxAccess_token = weChatRemote.getWxAccess_token();
            System.out.println(wxAccess_token);
        } catch (Exception e) {
            log.error("test4 error:{}", e);
        }
    }
}
