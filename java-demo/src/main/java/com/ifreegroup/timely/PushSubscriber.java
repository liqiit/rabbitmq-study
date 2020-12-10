package com.ifreegroup.timely;

import com.rabbitmq.client.*;

/**
 * Title: PushSubscriber
 * Description:push模式消费者，可以通过指定qos来限流，控制推送给消费者的消息数量
 * Company: iFree Group
 *
 * @author liqi
 * @date 2020/12/10
 */
public class PushSubscriber {
    private static final String RABBITMQ_USERNAME = "guest";
    private static final String RABBITMQ_PASSWORD = "guest";
    private static final String RABBITMQ_VIRTUALHOST = "/";
    private static final String RABBITMQ_HOSTNAME = "8.210.252.134";
    private static final int RABBITMQ_PORT = 5672;
    private static final String EXCHANGE_NAME = "pushExchange";

    public static void main(String[] argv) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(RABBITMQ_HOSTNAME);
        connectionFactory.setUsername(RABBITMQ_USERNAME);
        connectionFactory.setPassword(RABBITMQ_PASSWORD);
        connectionFactory.setVirtualHost(RABBITMQ_VIRTUALHOST);
        connectionFactory.setPort(RABBITMQ_PORT);

        try {
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT,true);
            String queueName = channel.queueDeclare().getQueue();
            channel.queueBind(queueName, EXCHANGE_NAME, "push");
            //根据指定的qos下发n条消息，全部收到ack后再发送n条消息
            channel.basicQos(2);
            System.out.println(" [*] Waiting for push messages.");

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
                //确认收到消息，只确认当前消息
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
            };
            //手动确认
            boolean autoAck = false;
            channel.basicConsume(queueName, autoAck, deliverCallback, consumerTag -> {
            });
        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
