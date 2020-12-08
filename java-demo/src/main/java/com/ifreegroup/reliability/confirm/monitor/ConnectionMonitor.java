package com.ifreegroup.reliability.confirm.monitor;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.impl.MicrometerMetricsCollector;
import com.rabbitmq.client.impl.StandardMetricsCollector;

/**
 * Title: ConnectionMonitor
 * Description:
 * Company: iFree Group
 *
 * @author liqi
 * @date 2020/12/8
 */
public class ConnectionMonitor {
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
            StandardMetricsCollector metrics = new StandardMetricsCollector();

            factory.setMetricsCollector(metrics);
            Connection connection = factory.newConnection();
            connection.addBlockedListener(reason -> {
                System.out.println("连接阻塞，原因：" + reason);
            }, () -> {
                System.out.println("连接未阻塞");
            });
            metrics.getPublishedMessages();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
