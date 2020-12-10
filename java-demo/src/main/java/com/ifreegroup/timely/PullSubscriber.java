package com.ifreegroup.timely;

import com.rabbitmq.client.*;

/**
 * Title: PullSubscriber
 * Description: pull模式消费者掌握主动权，主动向broker获取消息
 * Company: iFree Group
 *
 * @author liqi
 * @date 2020/12/10
 */
public class PullSubscriber {
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
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT, true);
            String queueName = channel.queueDeclare().getQueue();
            channel.queueBind(queueName, EXCHANGE_NAME, "push");
            System.out.println(" [*] Waiting for push messages.");
            //手动确认
            boolean autoAck = false;
            while (true) {
                GetResponse getResponse = channel.basicGet(queueName, autoAck);
                if (getResponse != null) {
                    if (getResponse.getMessageCount() == 0) {
                        break;
                    }
                    String message = new String(getResponse.getBody(), "UTF-8");
                    System.out.println(" [x] Received '" + message + "'");
                    channel.basicAck(getResponse.getEnvelope().getDeliveryTag(), false);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
