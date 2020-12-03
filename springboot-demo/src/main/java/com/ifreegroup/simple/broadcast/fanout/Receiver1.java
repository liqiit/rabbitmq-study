package com.ifreegroup.simple.broadcast.fanout;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

/**
 * Title: Receiver1
 * Description: 扇出消息接收者1
 * Company: iFree Group
 *
 * @author liqi
 * @date 2020/12/2
 */
@Component(value = "fanoutExchangeReceiver1")
@RabbitListener(
        bindings = @QueueBinding(
                value = @Queue(
                        value = "fanoutQueue1",
                        autoDelete = "true",
                        exclusive = "false"
                ),
                exchange = @Exchange(
                        value = "fanoutExchange",
                        type = ExchangeTypes.FANOUT //默认为direct类型
                ),
                key = {"fanout1"} //routekey 可以绑定多个
        )
)
public class Receiver1 {
    @RabbitHandler
    public void receive(String message) {
        System.out.println("instance1  [x] Received '" + message + "'");
    }
}
