package com.ifreegroup.reliability.connection;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.*;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.util.StringUtils;

/**
 * Title: AutoRecoverConfiguration
 * Description:
 * RabbitAdmin 在连接失败的情况下会自动重新创建queues, exchanges, bindings等信息
 * 通过retryTemplate设置重试策略
 * Company: iFree Group
 *
 * @author liqi
 * @date 2020/12/7
 */
@Configuration
public class AutoRecoverConfiguration {
//    private final String[] adminUris = {"http://8.210.252.134:15672", "http://47.242.63.83:15672", "http://47.242.168.10:15672"};
//
//    private final String[] nodes = {"rabbit@rabbitmq01", "rabbit@rabbitmq02","rabbit@rabbitmq03"};
//
//    @Bean("cachConnectionFactory")
//    public ConnectionFactory cachConnectionFactory() {
//
//        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
//
//        connectionFactory.setCacheMode(CachingConnectionFactory.CacheMode.CONNECTION);
//        connectionFactory.setAddresses("");
//        //设置为true，address列表将随机获取一个address创建连接
//        connectionFactory.setShuffleAddresses(true);
//        //开启Publisher确认模式
//        connectionFactory.setPublisherConfirmType(CachingConnectionFactory.ConfirmType.CORRELATED);
//        connectionFactory.setPublisherReturns(true);
//
//        return connectionFactory;
//    }
//
//    /***
//     * 通过rest api将指定queue所在的节点指定为master
//     * @param defaultCF
//     * @return
//     */
//    @Bean("localConnectionFactroy")
//    public ConnectionFactory localConnectionFactroy(@Qualifier("cachConnectionFactory") ConnectionFactory defaultCF) {
//        return new LocalizedQueueConnectionFactory(
//                defaultCF,
//                StringUtils.commaDelimitedListToStringArray("8.210.252.134:5672,47.242.63.83:5672,47.242.168.10:5672"),
//                this.adminUris,
//                this.nodes,
//                "/",
//                "guest",
//                "guest",
//                false, null);
//    }
//
//    /***
//     * 获取与当前线程绑定的连接工厂，可用配置路由key
//     * @return
//     */
//    @Bean("simpleRoutingConnectionFactory")
//    public ConnectionFactory simpleRoutingConnectionFactory() {
//        ConnectionFactory connectionFactory = new SimpleRoutingConnectionFactory();
//        SimpleResourceHolder.bind("", "");
//        return connectionFactory;
//    }
//
//    @Bean
//    public AmqpAdmin amqpAdmin() {
//        RabbitAdmin rabbitAdmin= new RabbitAdmin(cachConnectionFactory());
//        RetryTemplate retryTemplate = new RetryTemplate();
//        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
//        backOffPolicy.setInitialInterval(500);
//        backOffPolicy.setMultiplier(10.0);
//        backOffPolicy.setMaxInterval(10000);
//        retryTemplate.setBackOffPolicy(backOffPolicy);
//        rabbitAdmin.setRetryTemplate(retryTemplate);
//        return rabbitAdmin;
//    }
//
//    @Bean
//    public RabbitTemplate rabbitTemplate() {
//        RabbitTemplate template = new RabbitTemplate(cachConnectionFactory());
//        RetryTemplate retryTemplate = new RetryTemplate();
//        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
//        backOffPolicy.setInitialInterval(500);
//        backOffPolicy.setMultiplier(10.0);
//        backOffPolicy.setMaxInterval(10000);
//        retryTemplate.setBackOffPolicy(backOffPolicy);
//        template.setRetryTemplate(retryTemplate);
//        return template;
//    }
//
//
//    @Bean
//    public Queue myQueue() {
//        return new Queue("myqueue");
//    }
}
