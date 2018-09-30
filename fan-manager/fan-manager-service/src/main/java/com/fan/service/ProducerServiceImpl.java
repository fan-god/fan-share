package com.fan.service;


import com.fan.interfaces.IProducerService;
import org.apache.activemq.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;

public class ProducerServiceImpl implements IProducerService {

    @Autowired
    JmsTemplate jmsTemplate;
    @Resource(name = "queueDestination")
//	@Resource(name = "topicDestination")
    Destination destination;

    @Override
    public void sendMessage(final String message) {
        //使用JmsTemplate发送消息
        jmsTemplate.send(destination, new MessageCreator() {
            //创建消息
            @Override
            public Message createMessage(Session session) throws JMSException {
                TextMessage textMessage = session.createTextMessage(message);
                return (Message) textMessage;
            }
        });
        System.out.println("发送的消息：" + message);
    }

}
