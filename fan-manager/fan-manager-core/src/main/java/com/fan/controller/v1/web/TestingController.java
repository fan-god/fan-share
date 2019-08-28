package com.fan.controller.v1.web;

import com.alibaba.fastjson.JSONObject;
import com.fan.annotation.ApiVersion;
import com.fan.entity.ResponseMsg;
import com.fan.util.DateConvertEditor;
import com.fan.util.InternationalUtil;
import com.fan.util.RedisUtil;
import com.fan.util.SendMailUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.util.Date;
import java.util.Map;

/**
 * @author fan
 * @title: TestingController
 * @projectName fan-share
 * @description: TODO
 * @date 2019/7/26/002614:21
 */
@RestController
@RequestMapping("/web/testing/{version}")
@Slf4j
@ApiVersion
public class TestingController {

    @Autowired
    private RedisUtil redis;

    @GetMapping("/sendEmailBySweepCode")
    public ResponseMsg sendEmailBySweepCode() {
        String[] toMailAddr = new String[]{"495220742@qq.com", "chuanpu@vip.qq.com", "18627054863@163.com"};
        String subject = "测试邮件";
        String message = "测试邮件<br/><a href='http://m16808311q.iask.in:47429/fan-manager-web/api/testing/v1.0/sendEmailBySweepCode'>童俊是傻逼</a><br/>";
        try {
            File attachmentFile = new File("D:/fan-share/美女.png");
            SendMailUtil.SendMail sendMail = new SendMailUtil.SendMail.Builder("1129984165@qq.com")
                    .subject(subject)
                    .message(message)
                    .toMailAddr(toMailAddr)
                    .fromPwd("esumiavrldyoifhh")
                    .attachmentFile(attachmentFile)
                    .hostName("smtp.qq.com")
                    .smtpPort(25)
                    .build();
            System.out.println(sendMail);
            String result = SendMailUtil.sendCommonMail(sendMail);
            log.info(result);
            return ResponseMsg.success();
        } catch (Exception e) {
            log.error("sendEmailBySweepCode error:{}", e);
            return ResponseMsg.fail();
        }
    }

    @GetMapping("/test2")
    public ResponseMsg test2(@NotNull String key) {

        try {
            String message = InternationalUtil.getMessage(key);
            return ResponseMsg.success().setDatas(message);
        } catch (Exception e) {
            log.error("{}", e);
        }
        return ResponseMsg.fail();

    }

    @GetMapping("/test3")
    public ResponseMsg test3() {
        try {
            if (redis.addValue("qazxsw", "你有弄窘况", 10)) {
                System.out.println(123);
            }
            return ResponseMsg.success();
        } catch (Exception e) {
            log.error("SeckillController error:{}", e);
        }
        return ResponseMsg.fail();
    }

    @GetMapping("/test4")
    public ResponseMsg test4(Date birthday) {
        try {
            System.out.println(DateConvertEditor.getDateTime(birthday));
            return ResponseMsg.success();
        } catch (Exception e) {
            log.error("SeckillController error:{}", e);
        }
        return ResponseMsg.fail();
    }

    @PostMapping("/test5")
    public ResponseMsg test5(@RequestBody JSONObject json) {
        try {
            System.out.println(json);
            return ResponseMsg.success();
        } catch (Exception e) {
            log.error("SeckillController error:{}", e);
        }
        return ResponseMsg.fail();
    }
}
