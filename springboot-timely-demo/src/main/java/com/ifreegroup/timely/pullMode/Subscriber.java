package com.ifreegroup.timely.pullMode;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Title: Subscriber
 * Description: 使用pull模式，消费者可以控制取消息的规则，但及时性不强
 * Company: iFree Group
 *
 * @author liqi
 * @date 2020/12/10
 */
@Component("pullSubscriber")
public class Subscriber  {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Scheduled(fixedDelay = 5000)
    public void pullMessage() {
        Object message = rabbitTemplate.receiveAndConvert("pullQueue");
        if(message!=null){
            System.out.println("收到消息:" + String.valueOf(message));
        }

    }

}
