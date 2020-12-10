package com.ifreegroup.timely.pullMode;

import org.springframework.amqp.core.DirectExchange;
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
@Component
public class Publisher {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private DirectExchange pullExchange;

    public void publish() {
        for (int i = 0; i < 100; i++) {
            rabbitTemplate.convertAndSend(pullExchange.getName(), "pull", "hello pull message " + i);
        }
    }
}
