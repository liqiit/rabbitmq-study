package com.ifreegroup.timely.pushMode;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Title: Subscriber
 * Description: 使用push模式
 * Company: iFree Group
 *
 * @author liqi
 * @date 2020/12/10
 */
@Component("pushSubscriber")
public class Subscriber {
    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(
                            value = "pushQueue",
                            autoDelete = "false",
                            exclusive = "false"
                    ),
                    exchange = @Exchange(
                            value = "pushExchange",
                            type = ExchangeTypes.DIRECT //默认为direct类型
                    ),
                    key = {"push"} //routekey 可以绑定多个
            )
    )
    public void receiveMessage(String message) {
        System.out.println("收到消息：" + message);
    }

}
