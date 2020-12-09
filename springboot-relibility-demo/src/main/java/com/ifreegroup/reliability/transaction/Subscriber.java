package com.ifreegroup.reliability.transaction;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Title: Subscriber
 * Description:
 * Company: iFree Group
 *
 * @author liqi
 * @date 2020/12/9
 */
@Component(value = "txSubscriber")
public class Subscriber {
    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(
                            value = "txQueue",
                            autoDelete = "false",
                            exclusive = "false"
                    ),
                    exchange = @Exchange(
                            value = "txExchange",
                            type = ExchangeTypes.DIRECT //默认为direct类型
                    ),
                    key = {"tx"} //routekey 可以绑定多个
            )
    )
    public void receive(Channel channel, Message message) throws IOException {
       try {
           long deliveryTag = message.getMessageProperties().getDeliveryTag();
           //手工ack
           channel.basicAck(deliveryTag,true);
           System.out.println("receive2: " + new String(message.getBody()));
       }catch (Exception e){
           if (message.getMessageProperties().getRedelivered()) {
               System.out.println("消息已重复处理失败,拒绝再次接收" );
               // 拒绝消息，requeue=false 表示不再重新入队，如果配置了死信队列则进入死信队列
               channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
           } else {
               System.out.println("消息即将再次返回队列处理" );
               // requeue为是否重新回到队列，true重新入队
               channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
           }
       }

    }

}
