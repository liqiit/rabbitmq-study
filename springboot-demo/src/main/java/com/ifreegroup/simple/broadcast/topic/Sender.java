package com.ifreegroup.simple.broadcast.topic;

import org.springframework.amqp.core.TopicExchange;
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
@Component(value = "topicExchangeSender")
public class Sender {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private TopicExchange topicExchange;

    public void publish(String bindKey) {
        for (int i = 0; i < 100; i++) {
            rabbitTemplate.convertAndSend(topicExchange.getName(), bindKey, "topic message " + i);
        }
    }
}
