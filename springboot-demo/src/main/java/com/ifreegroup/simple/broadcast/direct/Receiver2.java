package com.ifreegroup.simple.broadcast.direct;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Title: Receiver1
 * Description:
 * Company: iFree Group
 *
 * @author liqi
 * @date 2020/12/2
 */
@Component(value = "directExchangeReceiver2")
@RabbitListener(queues = "#{autoDeleteQueue2.name}")
public class Receiver2 {
    @RabbitHandler
    public void receive(String message) {
        System.out.println("instance2  [x] Received '" + message + "'");
    }
}
