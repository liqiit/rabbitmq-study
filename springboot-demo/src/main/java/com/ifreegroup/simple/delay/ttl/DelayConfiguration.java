package com.ifreegroup.simple.delay.ttl;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Title: DelayConfiguration
 * Description:延迟消息配置
 * Company: iFree Group
 *
 * @author liqi
 * @date 2020/12/2
 */
@Configuration
public class DelayConfiguration {

    //包含有效期的队列
    @Bean(value = "ttlQueue")
    public Queue ttlQueue() {
        return QueueBuilder
                .durable("ttlQueue")
                .deadLetterExchange("deadExchange")
                .deadLetterRoutingKey("ttl")
                //.ttl(2000)//队列中所有消息延时时间相同
                .build();
    }

    //包含有消息队列绑定的交换机
    @Bean("ttlExchange")
    public DirectExchange ttlExchange() {
        return ExchangeBuilder.directExchange("ttlExchange").build();
    }
    @Bean("ttlBinding")
    public Binding ttlBinding() {
        return BindingBuilder
                .bind(ttlQueue())
                .to(ttlExchange())
                .with("ttl");
    }

    //死信队列
    @Bean("deadQueue")
    public Queue deadQueue() {
        return QueueBuilder.durable("deadQueue").build();
    }
    //死信队列绑定的交换机
    @Bean("deadExchange")
    public DirectExchange deadExchange(){
        return ExchangeBuilder.directExchange("deadExchange").build();

    }
    @Bean("bindingDead")
    public Binding bindingDead(){
        return BindingBuilder.bind(deadQueue()).to(deadExchange()).with("ttl");
    }



}
