package com.ifreegroup.simple.broadcast.fanout;

import com.rabbitmq.client.*;

/**
 * Title: SubscriberError
 * Description: exchange类型为fanout的消费者忽略bindkey，接收所有发布的消息
 * Company: iFree Group
 *
 * @author liqi
 * @date 2020/11/23
 */
public class SubscriberError {
    private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] argv) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try {
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT,true);
            String queueName = channel.queueDeclare().getQueue();
            channel.queueBind(queueName, EXCHANGE_NAME, "error");

            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
            };
            channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
