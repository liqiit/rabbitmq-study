package com.ifreegroup.timely.pushMode;

import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Title: Publisher
 * Description:
 * Company: iFree Group
 *
 * @author liqi
 * @date 2020/12/10
 */
@Component("pushPublisher")
public class Publisher {
    @Autowired
    private Exchange pushExchange;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void pushPublish() {
        for (int i = 0; i < 100; i++) {
            rabbitTemplate.convertAndSend(pushExchange.getName(), "push", "hello push message " + i);
        }
    }
}
