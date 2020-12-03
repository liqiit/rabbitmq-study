package com.ifreegroup.simple.broadcast.direct;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Title: Sender
 * Description:
 * Company: iFree Group
 *
 * @author liqi
 * @date 2020/12/2
 */
@Component(value = "directExchangeSender")
public class Sender {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private DirectExchange directExchange;

    public void publish(String routeKey) {
        for (int i = 0; i < 10; i++) {
            rabbitTemplate.convertAndSend(directExchange.getName(), routeKey, "direct message " + i);
        }
    }
}
