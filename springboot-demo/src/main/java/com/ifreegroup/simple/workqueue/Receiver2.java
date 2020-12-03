package com.ifreegroup.simple.workqueue;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

/**
 * Title: Receiver1
 * Description:
 * Company: iFree Group
 *
 * @author liqi
 * @date 2020/12/2
 */
@Component(value = "receiver2")
@RabbitListener(queues = {"workqueue"})
public class Receiver2 {

    @RabbitHandler
    public void receive(String message) {
        System.out.println("instance2 [x] Received '" + message + "'");
    }
}
