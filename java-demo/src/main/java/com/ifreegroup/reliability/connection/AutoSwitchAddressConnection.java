package com.ifreegroup.reliability.connection;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Title: AutoSwitchAddressConnection
 * Description: 自动切换服务节点与自动恢复互斥
 * Company: iFree Group
 *
 * @author liqi
 * @date 2020/12/7
 */
public class AutoSwitchAddressConnection {
    private static final String RABBITMQ_USERNAME = "guest";
    private static final String RABBITMQ_PASSWORD = "guest";
    private static final String RABBITMQ_VIRTUALHOST = "/";
    private static final String RABBITMQ_HOSTNAME = "8.210.252.134";
    private static final int RABBITMQ_PORT = 5672;

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        //用户guest默认只能连接本地服务器，如果需要连接远程服务需要
        factory.setUsername(RABBITMQ_USERNAME);
        factory.setPassword(RABBITMQ_PASSWORD);
        factory.setVirtualHost(RABBITMQ_VIRTUALHOST);
        //factory.setHost(RABBITMQ_HOSTNAME);
        factory.setPort(RABBITMQ_PORT);
        //连接自动恢复配置项, 4.0.0之后版本默认启动
        factory.setAutomaticRecoveryEnabled(false);
        //如果第一次恢复连接失败，后续每隔10秒尝试恢复一次
        factory.setNetworkRecoveryInterval(10000);
        //进行自动尝试连接其他服务器
        Address[] addresses = {new Address("8.210.252.134"),new Address("47.242.63.83"), new Address("47.242.168.10")};
        //如果自动重连不能成功，需要手动捕获异常增加重连逻辑
        Connection conn;
        Channel channel;
        try {
            conn = factory.newConnection(addresses);
            //设置连接的唯一标识，名称将被忽略
            channel=conn.openChannel().get();
            System.out.println(channel.getConnection());
        } catch (java.net.ConnectException e) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            //进行重连
            conn = factory.newConnection(addresses);

        }finally {
//            if(channel!=null){
//                //可以不显示调用，连接关闭会自动关闭通道
//                channel.close();
//            }
//            if(conn!=null){
//                conn.close();
//            }

        }

    }
}
