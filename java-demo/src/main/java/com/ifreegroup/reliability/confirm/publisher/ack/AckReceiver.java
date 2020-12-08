package com.ifreegroup.reliability.confirm.publisher.ack;

import com.rabbitmq.client.*;

/**
 * Title: TxReceiver
 * Description: 事务消息接收者
 * Company: iFree Group
 *
 * @author liqi
 * @date 2020/12/3
 */
public class AckReceiver {
    private static final String RABBITMQ_USERNAME = "guest";
    private static final String RABBITMQ_PASSWORD = "guest";
    private static final String RABBITMQ_VIRTUALHOST = "/";
    private static final String RABBITMQ_HOSTNAME = "127.0.0.1";
    private static final int RABBITMQ_PORT = 5672;
    private final static String QUEUE_NAME = "publishAckMsgQueue";

    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(RABBITMQ_HOSTNAME);
        connectionFactory.setUsername(RABBITMQ_USERNAME);
        connectionFactory.setPassword(RABBITMQ_PASSWORD);
        connectionFactory.setVirtualHost(RABBITMQ_VIRTUALHOST);
        connectionFactory.setPort(RABBITMQ_PORT);
        connectionFactory.getClientProperties().put("client","AckReceiver");
        try {
            Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel();
            channel.exchangeDeclare("publishAckMsgExchange", BuiltinExchangeType.DIRECT, true);
            channel.queueDeclare(QUEUE_NAME, true, false, false, null).getQueue();

            channel.queueBind(QUEUE_NAME, "publishAckMsgExchange", "publishConfirm");
            System.out.println(" [*] Waiting for messages. ");
            channel.basicQos(1);
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                AMQP.BasicProperties properties = delivery.getProperties();
                String messageID = properties.getMessageId();
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println(" [x] Received TxMessage ID:'" + messageID + ",content:" + message + "',deliverTag:"+delivery.getEnvelope().getDeliveryTag());
                try {
                    doWork();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("AckMessage '" + message + "' has done");
                    channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                }
            };
            channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void doWork() throws InterruptedException {
        Thread.sleep(1000);
    }
}
