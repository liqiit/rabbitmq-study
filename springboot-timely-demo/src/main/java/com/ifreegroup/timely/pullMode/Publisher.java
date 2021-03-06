package com.ifreegroup.timely.pullMode;

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
@Component("pullPublisher")
public class Publisher {
    @Autowired
    private Exchange pullExchange;
    @Autowired
    private RabbitTemplate rabbitTemplate;


    public void pullPublish() {
        for (int i = 0; i < 100; i++) {
            rabbitTemplate.convertAndSend(pullExchange.getName(), "pull", "hello pull message " + i);
        }
    }
}
