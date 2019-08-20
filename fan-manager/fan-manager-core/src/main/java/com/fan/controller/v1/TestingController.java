package com.fan.controller.v1;

import com.fan.annotation.ApiVersion;
import com.fan.entity.Msg;
import com.fan.util.InternationalUtil;
import com.fan.util.SendMailUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

/**
 * @author fan
 * @title: TestingController
 * @projectName fan-share
 * @description: TODO
 * @date 2019/7/26/002614:21
 */
@RestController
@RequestMapping("/api/testing/{version}")
@Slf4j
@ApiVersion
public class TestingController {

    @GetMapping("/sendEmailBySweepCode")
    public Msg sendEmailBySweepCode() {
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
            return Msg.success();
        } catch (Exception e) {
            log.error("sendEmailBySweepCode error:{}", e);
            return Msg.fail();
        }
    }

    @GetMapping("/test2")
    public Msg test2(String key) {
        String message = InternationalUtil.getMessage(key);
        return Msg.success().setDatas(message);
    }
}
