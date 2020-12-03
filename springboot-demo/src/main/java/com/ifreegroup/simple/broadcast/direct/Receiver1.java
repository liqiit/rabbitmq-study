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
@Component(value = "directExchangeReceiver1")
@RabbitListener(queues = "#{autoDeleteQueue1.name}")
public class Receiver1 {
    @RabbitHandler
    public void receive(String message) {
        System.out.println("instance1  [x] Received '" + message + "'");
    }
}
