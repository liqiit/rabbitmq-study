package com.ifreegroup.reliability.transaction;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.transaction.RabbitTransactionManager;
import org.springframework.context.annotation.Bean;

/**
 * Title: TransactionConfig
 * Description: 事务性消息配置
 * Company: iFree Group
 *
 * @author liqi
 * @date 2020/12/9
 */
public class TransactionConfig {
    @Bean("txQueue")
    public Queue txQueue() {
        return QueueBuilder.durable("txQueue").build();//new AnonymousQueue();
    }

    @Bean("txExchange")
    public DirectExchange txExchange() {
        return ExchangeBuilder.directExchange("txExchange").durable(true).build();
    }
    @Bean
    public Binding txBinding() {
        return BindingBuilder.bind(txQueue()).to(txExchange()).with("tx");
    }
    /**
     * 配置启用rabbitmq事务
     * @param connectionFactory
     * @return
     */
    @Bean("rabbitTransactionManager")
    public RabbitTransactionManager rabbitTransactionManager(CachingConnectionFactory connectionFactory) {
        return new RabbitTransactionManager(connectionFactory);
    }
}
