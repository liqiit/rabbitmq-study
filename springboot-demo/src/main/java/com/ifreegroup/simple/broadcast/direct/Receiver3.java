package com.ifreegroup.simple.broadcast.direct;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

/**
 * Title: Receiver1
 * Description:
 * Company: iFree Group
 *
 * @author liqi
 * @date 2020/12/2
 */
@Component(value = "directExchangeReceiver3")
@RabbitListener(
        bindings = @QueueBinding(
                value = @Queue(
                        value = "autoDeleteQueue1",
                        autoDelete = "true",
                        exclusive = "false"
                ),
                exchange = @Exchange(
                        value = "directExchange",
                        type = ExchangeTypes.DIRECT //默认为direct类型
                ),
                key = {"direct2","direct"} //routekey 可以绑定多个
        )
)
public class Receiver3 {
    @RabbitHandler
    public void receive(String message) {
        System.out.println("instance3  [x] Received '" + message + "'");
    }
}
