package com.ifreegroup.simple.broadcast.fanout;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * Title: DelayPublisher
 * Description: fanout类型exchange无法对消息进行条件约束，
 * 所有消费者都会收到消息
 * Company: iFree Group
 *
 * @author liqi
 * @date 2020/11/23
 */
public class Publisher {
    private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] args) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT,true);
            String message;
            for (int i = 0; i < 100; i++) {
                if (i % 2 == 0) {
                    message = "ErrorInfo " + i;
                    channel.basicPublish(EXCHANGE_NAME, "error", null, message.getBytes("UTF-8"));
                } else {
                    message = "WarnInfo " + i;
                    channel.basicPublish(EXCHANGE_NAME, "warn", null, message.getBytes("UTF-8"));
                }
                System.out.println(" [x] Sent '" + message + "'");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
