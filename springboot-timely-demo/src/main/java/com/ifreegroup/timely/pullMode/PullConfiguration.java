package com.ifreegroup.timely.pullMode;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Title: PullConfiguration
 * Description:
 * Company: iFree Group
 *
 * @author liqi
 * @date 2020/12/10
 */
@Configuration
public class PullConfiguration {

    @Bean
    public RabbitTemplate amqpTemplate() {
        return new RabbitTemplate();
    }

    @Bean
    public DirectExchange pullDirectExchange() {
        return ExchangeBuilder
                .directExchange("pullExchange")
                .durable(true)
                .build();
    }

    @Bean
    public Queue pullQueue() {
        return QueueBuilder.durable("pullQueue").lazy().build();
    }

    @Bean
    public Binding pullBinding() {
        return BindingBuilder.bind(pullQueue()).to(pullDirectExchange()).with("pull");
    }
}
