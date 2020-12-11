package com.ifreegroup.reliability.confirm.consumer;

import com.rabbitmq.client.*;

/**
 * Title: AutoAckReceiver
 * Description: 自动确认消息接收者
 * Company: iFree Group
 *
 * @author liqi
 * @date 2020/12/3
 */
public class AutoAckReceiver {
    private static final String RABBITMQ_USERNAME = "guest";
    private static final String RABBITMQ_PASSWORD = "guest";
    private static final String RABBITMQ_VIRTUALHOST = "/";
    private static final String RABBITMQ_HOSTNAME = "localhost";
    private static final int RABBITMQ_PORT = 5672;
    private final static String QUEUE_NAME = "autoAckDemoQueue";

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
            channel.exchangeDeclare("ackDemoExchange", BuiltinExchangeType.DIRECT,true);
            channel.queueDeclare(QUEUE_NAME,true,false,false,null).getQueue();

            channel.queueBind(QUEUE_NAME, "ackDemoExchange", "autoAck");
            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
            channel.basicQos(1);
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), "UTF-8");
                System.err.println(" [x] Received '" + message + "'");
                try {
                    doWork();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("message '" + message + "' has done");
                    channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                }
            };
            channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {
            });
            //增加连接恢复监听器
            ((Recoverable)connection).addRecoveryListener(new RecoveryListener() {
                @Override
                public void handleRecovery(Recoverable recoverable) {
                    System.out.println("连接自动恢复已完成");
                }

                @Override
                public void handleRecoveryStarted(Recoverable recoverable) {
                    System.out.println("连接自动恢复开始启动");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void doWork() throws InterruptedException {
        Thread.sleep(1000);
    }
}
