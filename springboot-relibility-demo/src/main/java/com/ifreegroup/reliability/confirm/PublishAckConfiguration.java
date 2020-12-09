package com.ifreegroup.reliability.confirm;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;

/**
 * Title: PublishAckConfiguration
 * Description: 发布者确认
 * Company: iFree Group
 *
 * @author liqi
 * @date 2020/12/8
 */
@Slf4j
public class PublishAckConfiguration {

//    @Bean
//    public ConnectionFactory publishAckConnectionFactory() {
//        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
//        connectionFactory.setHost("localhost");
//        connectionFactory.setPort(5672);
//        //connectionFactory.setAddresses("8.210.252.134:5672,47.242.168.10:5672,47.242.63.83:5672");
//        connectionFactory.setCacheMode(CachingConnectionFactory.CacheMode.CONNECTION);
//        //开启Publisher确认模式
//        connectionFactory.setPublisherConfirmType(CachingConnectionFactory.ConfirmType.CORRELATED);
//        //发布回调标识必须为true
//        connectionFactory.setPublisherReturns(true);
//        return connectionFactory;
//    }

    @Bean("publishAckQueue")
    public Queue publishAckQueue() {
        return QueueBuilder.durable("publishAckQueue").build();//new AnonymousQueue();
    }

    @Bean("publishAckExchange")
    public DirectExchange publishAckExchange() {
        return ExchangeBuilder.directExchange("publishAckExchange").durable(true).build();
    }
//
//    @Bean("publishAckTemplate")
//    public RabbitTemplate publishAckTemplate() {
//        RabbitTemplate template = new RabbitTemplate(publishAckConnectionFactory());
//        //需要对returnedMessage做处理，此处为true
//        template.setMandatory(true);
//        //Publish to an exchange but there is no matching destination queue.
//        template.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
//            log.warn("发送到exchange[" + exchange + "],routeKey[" + routingKey + "]消息[" + message + "]响应码[" + replyCode + "]响应内容[" + replyText + "]");
//        });
//        //
//        template.setConfirmCallback((correlationData, ack, cause) -> {
//            log.info("消息" + correlationData + "确认标示[" + ack + "]原因[" + cause + "]");
//        });
//        RetryTemplate retryTemplate = new RetryTemplate();
//        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
//        backOffPolicy.setInitialInterval(500);
//        backOffPolicy.setMultiplier(10.0);
//        backOffPolicy.setMaxInterval(10000);
//        retryTemplate.setBackOffPolicy(backOffPolicy);
//        template.setRetryTemplate(retryTemplate);
//        return template;
//    }

    @Bean
    public Binding publishAckBinding() {
        return BindingBuilder.bind(publishAckQueue()).to(publishAckExchange()).with("publishAck");
    }
}
