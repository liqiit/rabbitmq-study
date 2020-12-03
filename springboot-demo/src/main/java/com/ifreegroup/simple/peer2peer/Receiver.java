package com.ifreegroup.simple.peer2peer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

/**
 * Title: Receiver1
 * Description: 点对点消息接收者
 * Company: iFree Group
 *
 * @author liqi
 * @date 2020/12/2
 */
@RabbitListener(queues = "hello",exclusive = true)
public class Receiver {
    @RabbitHandler
    public void receive(String message) {
        System.out.println(" [x] Received '" + message + "'");
    }
}
