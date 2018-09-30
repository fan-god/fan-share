package com.fan.listener;

import com.fan.interfaces.IIntegralService;
import com.fan.interfaces.IUserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.HashMap;
import java.util.Map;

public class ActiveMqListener implements MessageListener {
    private final static Logger log = Logger.getLogger(ActiveMqListener.class);
    @Autowired
    private IIntegralService iIntegralService;
    @Autowired
    private IUserService iUserService;

    @Override
    public void onMessage(Message message) {
        Map<String, Object> map = new HashMap<>();
        TextMessage textMessage = (TextMessage) message;
        try {
            String msg = textMessage.getText();
            if (StringUtils.isNotBlank(msg)) {
                int score = iIntegralService.getScoreByModu(msg);
                map.put("score", score);
                map.put("username", "lQOwWfB6gT");
                int count = iUserService.AddGwf1(map);
                if (count > 0) {
                    System.out.println("用户lQOwWfB6gT增加" + score + "积分");
                }
            } else {
                System.out.println("队列现在为空");
            }
        } catch (JMSException e) {
            log.error("监听消息出现异常，异常信息：" + e.getMessage());
        }
    }

}
