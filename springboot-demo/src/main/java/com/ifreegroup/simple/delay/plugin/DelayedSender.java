package com.ifreegroup.simple.delay.plugin;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Title: DelayedSender
 * Description:
 * Company: iFree Group
 *
 * @author liqi
 * @date 2020/12/2
 */
@Component(value = "pluginDelaySender")
public class DelayedSender {
    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send() {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (int i = 0; i < 10; i++) {
            Date now = new Date();
            String dlxMsg = "publish at " + sf.format(now);
            rabbitTemplate.convertAndSend("pluginDelayExchange", "pluginDelayQueue", dlxMsg, message -> {
                message.getMessageProperties().setHeader("x-delay", 3000);
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