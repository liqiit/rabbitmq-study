package com.ifreegroup.timely;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * Title: Publisher
 * Description: 消息发布者
 * Company: iFree Group
 *
 * @author liqi
 * @date 2020/12/10
 */
public class Publisher {
    private static final String RABBITMQ_USERNAME = "guest";
    private static final String RABBITMQ_PASSWORD = "guest";
    private static final String RABBITMQ_VIRTUALHOST = "/";
    private static final String RABBITMQ_HOSTNAME = "8.210.252.134";
    private static final int RABBITMQ_PORT = 5672;
    private static final String EXCHANGE_NAME = "pushExchange";

    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(RABBITMQ_HOSTNAME);
        connectionFactory.setUsername(RABBITMQ_USERNAME);
        connectionFactory.setPassword(RABBITMQ_PASSWORD);
        connectionFactory.setVirtualHost(RABBITMQ_VIRTUALHOST);
        connectionFactory.setPort(RABBITMQ_PORT);
        try (
                Connection connection = connectionFactory.newConnection();
                Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT, true);
            for (int i = 0; i < 100; i++) {
                String message = "hello push message " + i;
                channel.basicPublish(EXCHANGE_NAME, "push", null, message.getBytes("UTF-8"));
                System.out.println(" [x] Send message '" + message + "'");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
