package com.ifreegroup.simple.broadcast.fanout;

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
@Component(value = "fanoutExchangeReceiver2")
@RabbitListener(
        bindings = @QueueBinding(
                value = @Queue(
                        value = "fanoutQueue2",
                        autoDelete = "true",
                        exclusive = "false"
                ),
                exchange = @Exchange(
                        value = "fanoutExchange",
                        type = ExchangeTypes.FANOUT //默认为direct类型
                ),
                key = {"fanout2"} //routekey 可以绑定多个
        )
)
public class Receiver2 {
    @RabbitHandler
    public void receive(String message) {
        System.out.println("instance2  [x] Received '" + message + "'");
    }
}
