package com.ifreegroup.simple.broadcast.topic;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

/**
 * Title: Receiver3
 * Description:
 * Company: iFree Group
 *
 * @author liqi
 * @date 2020/12/2
 */
@Component(value = "topicExchangeReceiver3")
@RabbitListener(
        bindings = @QueueBinding(
                value = @Queue(
                        value = "topicQueue3",
                        autoDelete = "true",
                        exclusive = "false"
                ),
                exchange = @Exchange(
                        value = "topicExchange",
                        type = ExchangeTypes.TOPIC //默认为direct类型
                ),
                key = {"#.food.*"} //routekey 可以绑定多个
        )
)
public class Receiver3 {
    @RabbitHandler
    public void receive(String message) {
        System.out.println("instance3  [x] Received '" + message + "'");
    }
}
