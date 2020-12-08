package com.ifreegroup.reliability.confirm.publisher.transactional;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

/**
 * Title: TxPublisher
 * Description:事务channel不能进入确认模式
 * 如果broker在从内存将消息写入磁盘的过程中宕机，有可能发生丢失消息的风险
 * 通过事务保证消息的可靠性，AMQP 0.9.1只有此一种方式，重量级，
 * 会大大降低吞吐量（官方数据会降低250倍）
 * Company: iFree Group
 *
 * @author liqi
 * @date 2020/12/8
 */
public class TxPublisher {
    private static final String RABBITMQ_USERNAME = "guest";
    private static final String RABBITMQ_PASSWORD = "guest";
    private static final String RABBITMQ_VIRTUALHOST = "/";
    private static final String RABBITMQ_HOSTNAME = "47.242.63.83";
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
            //打开事务
            AMQP.Tx.SelectOk selectOk = channel.txSelect();
            System.out.println("selectOK:" + selectOk);
            channel.exchangeDeclare("txMsgExchange", BuiltinExchangeType.DIRECT, true);
            for (int i = 0; i < 10; i++) {
                String message = "hello txMessage " + i;
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
                channel.basicPublish("txMsgExchange", "tx", props, message.getBytes("UTF-8"));
                System.out.println("send commitOk ,currentTime:"+System.currentTimeMillis());
                AMQP.Tx.CommitOk commitOk = channel.txCommit();
                System.out.println("receive commitOk ,currentTime:"+System.currentTimeMillis());
            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
