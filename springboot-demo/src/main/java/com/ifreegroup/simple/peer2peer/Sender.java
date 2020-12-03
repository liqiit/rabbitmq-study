package com.ifreegroup.simple.peer2peer;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;

/**
 * Title: Sender
 * Description:
 * Company: iFree Group
 *
 * @author liqi
 * @date 2020/12/2
 */
public class Sender {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Resource(name = "hello")
    private Queue queue;

    public void send() {
        String message = "Hello World!";
        this.rabbitTemplate.convertAndSend(queue.getName(), message);
        System.out.println(" [x] Sent '" + message + "'");
    }
}
