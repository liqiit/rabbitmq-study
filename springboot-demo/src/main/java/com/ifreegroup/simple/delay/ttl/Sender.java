package com.ifreegroup.simple.delay.ttl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Title: Sender
 * Description:
 * Company: iFree Group
 *
 * @author liqi
 * @date 2020/12/2
 */
@Slf4j
@Component(value = "delayMessageSender")
public class Sender {
    @Autowired
    private DirectExchange ttlExchange;
    @Autowired
    private RabbitTemplate rabbitTemplate;


    public void send() {
        for (int i = 0; i < 10; i++) {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            Date now = new Date();
            String dlxMsg = "publish at " + sf.format(now);
            //rabbitTemplate.convertAndSend(ttlExchange.getName(), "ttl", dlxMsg);
            //指定每个消息延时时间
            rabbitTemplate.convertAndSend(ttlExchange.getName(), "ttl", dlxMsg,message -> {
                message.getMessageProperties().setExpiration("10000");
                return message;
            });
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
