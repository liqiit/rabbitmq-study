package com.ifreegroup.simple.broadcast.fanout;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Title: Sender
 * Description:扇出消息发送者
 * Company: iFree Group
 *
 * @author liqi
 * @date 2020/12/2
 */
@Component(value = "fanoutExchangeSender")
public class Sender {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private FanoutExchange fanoutExchange;

    public void publish(String bindKey) {
        for (int i = 0; i < 10; i++) {
            rabbitTemplate.convertAndSend(fanoutExchange.getName(), bindKey, "fanout message " + i);
        }
    }
}
