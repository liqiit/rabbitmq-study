package com.ifreegroup.simple.delay.ttl;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Title: DelayPublisher
 * Description:本示例演示延时队列，实现方式通过ttl+死信队列方式
 * 目前存在问题 不能正常工作
 * <p>
 * Company: iFree Group
 *
 * @author liqi
 * @date 2020/12/1
 */
public class DelayPublisher {

    public static void main(String[] args) {
        // 创建链接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("8.210.252.134");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {

            String dlxExchangeName = "dlx_exchange_name";
            String dlxRoutingKey = "dlx_routingKey";

            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            Date now = new Date();
            String dlxMsg = "publish at " + sf.format(now);
            AMQP.BasicProperties basicProperties = new AMQP.BasicProperties.Builder()
                    .deliveryMode(2)
                    .contentEncoding("UTF-8")
                    .expiration("6000") // 设置 6 秒中过期
                    .build();

            channel.basicPublish(dlxExchangeName, dlxRoutingKey, basicProperties, dlxMsg.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
