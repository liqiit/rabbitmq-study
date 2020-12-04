package com.ifreegroup.reliability.confirm.consumer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Title: Publisher
 * Description:消息发布者
 * Company: iFree Group
 *
 * @author liqi
 * @date 2020/12/3
 */
public class Publisher {
    private static final String RABBITMQ_USERNAME = "guest";
    private static final String RABBITMQ_PASSWORD = "guest";
    private static final String RABBITMQ_VIRTUALHOST = "/";
    private static final String RABBITMQ_HOSTNAME = "localhost";
    private static final int RABBITMQ_PORT = 5672;

    public static void main(String[] args) {
        ConnectionFactory factory = new ConnectionFactory();
        //用户guest默认只能连接本地服务器，如果需要连接远程服务需要
        factory.setUsername(RABBITMQ_USERNAME);
        factory.setPassword(RABBITMQ_PASSWORD);
        factory.setVirtualHost(RABBITMQ_VIRTUALHOST);
        factory.setHost(RABBITMQ_HOSTNAME);
        factory.setPort(RABBITMQ_PORT);
        try (Connection connection = factory.newConnection();
             Channel channel = connection.openChannel().get()) {
            channel.exchangeDeclare("ackDemoExchange", BuiltinExchangeType.DIRECT, true);
            for (int i = 0; i < 100; i++) {

                if (i % 2 == 0) {
                    String message = "hello autoAck " + i;
                    channel.basicPublish("ackDemoExchange", "autoAck", null, message.getBytes("UTF-8"));
                } else {
                    String message = "hello manualAck " + i;
                    channel.basicPublish("ackDemoExchange", "manualAck", null, message.getBytes("UTF-8"));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
