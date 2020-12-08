package com.ifreegroup.reliability.confirm.publisher.ack;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

/**
 * Title: AckPublisher
 * Description:开启确认模式消息发布示例
 * Company: iFree Group
 *
 * @author liqi
 * @date 2020/12/8
 */
public class AckPublisher {
    private static final String RABBITMQ_USERNAME = "guest";
    private static final String RABBITMQ_PASSWORD = "guest";
    private static final String RABBITMQ_VIRTUALHOST = "/";
    private static final String RABBITMQ_HOSTNAME = "127.0.0.1";
    private static final int RABBITMQ_PORT = 5672;

    public static void main(String[] args) {
        ConnectionFactory factory = new ConnectionFactory();
        //用户guest默认只能连接本地服务器，如果需要连接远程服务需要
        factory.setUsername(RABBITMQ_USERNAME);
        factory.setPassword(RABBITMQ_PASSWORD);
        factory.setVirtualHost(RABBITMQ_VIRTUALHOST);
        factory.setHost(RABBITMQ_HOSTNAME);
        factory.setPort(RABBITMQ_PORT);
        try {
            Connection connection = factory.newConnection();
            Channel channel = connection.openChannel().get();
            AMQP.Confirm.SelectOk selectOk = channel.confirmSelect();
            System.out.println("selectOK:" + selectOk);
            channel.exchangeDeclare("publishAckMsgExchange", BuiltinExchangeType.DIRECT, true);
            channel.addConfirmListener(new ConfirmListener() {
                @Override
                public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                    System.out.println("deliverTag:" + deliveryTag + "收到Ack");
                }

                @Override
                public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                    System.out.println("deliverTag:" + deliveryTag + "收到Nack");
                }
            });

            for (int i = 0; i < 100; i++) {
                String message = "hello ackMessage " + i;
                //设置属性，包括消息ID，
                AMQP.BasicProperties props = new AMQP.BasicProperties(
                        "text",
                        "utf-8",
                        new HashMap<>(),
                        1,
                        1,
                        "correlationId",
                        "replyTo",
                        "100",
                        UUID.randomUUID().toString(),
                        new Date(),
                        "type",
                        "guest",
                        "appId",
                        "clusterId"
                );
                //构建器设置属性值不生效
//                AMQP.BasicProperties props = new AMQP.BasicProperties();
//                props.builder().messageId(UUID.randomUUID().toString()).build();
                channel.basicPublish("publishAckMsgExchange", "publishConfirm", props, message.getBytes("UTF-8"));
                if (channel.waitForConfirms()) {
                    System.out.println("ack");
                } else {
                    System.out.println("nack");

                }
                //Thread.sleep(1000);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
