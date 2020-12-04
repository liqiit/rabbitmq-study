package com.ifreegroup.reliability.confirm.consumer;

import com.rabbitmq.client.*;

/**
 * Title: ManualAckReceiver
 * Description:手动确认消息接收者
 * Company: iFree Group
 *
 * @author liqi
 * @date 2020/12/3
 */
public class ManualAckReceiver {
    private static final String RABBITMQ_USERNAME = "guest";
    private static final String RABBITMQ_PASSWORD = "guest";
    private static final String RABBITMQ_VIRTUALHOST = "/";
    private static final String RABBITMQ_HOSTNAME = "localhost";
    private static final int RABBITMQ_PORT = 5672;
    private final static String QUEUE_NAME = "manualAckDemoQueue";

    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(RABBITMQ_HOSTNAME);
        connectionFactory.setUsername(RABBITMQ_USERNAME);
        connectionFactory.setPassword(RABBITMQ_PASSWORD);
        connectionFactory.setVirtualHost(RABBITMQ_VIRTUALHOST);
        connectionFactory.setPort(RABBITMQ_PORT);
        try {
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();
            channel.exchangeDeclare("ackDemoExchange", BuiltinExchangeType.DIRECT, true);
            channel.queueDeclare(QUEUE_NAME, true, false, false, null).getQueue();
            //只会消费bindkey为error的消息
            channel.queueBind(QUEUE_NAME, "ackDemoExchange", "manualAck");
            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
            //每次只取一条处理
            channel.basicQos(1);
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
                try {
                    doWork();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("deliverTag:" + delivery.getEnvelope().getDeliveryTag() + ",message '" + message + "' has done");
                    channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                }
            };
            channel.basicConsume(QUEUE_NAME, false, deliverCallback, consumerTag -> {
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void doWork() throws InterruptedException {
        Thread.sleep(1000);
    }
}
