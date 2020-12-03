package com.ifreegroup.simple.workqueue;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

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
    @Resource(name = "workqueue")
    private Queue queue;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send() {
        for (int i = 0; i < 100; i++) {
            StringBuilder builder = new StringBuilder("Hello");
            builder.append(i);
            String message = builder.toString();
            rabbitTemplate.convertAndSend(queue.getName(), message);
            System.out.println(" [x] Sent '" + message + "'");
        }
    }
}
