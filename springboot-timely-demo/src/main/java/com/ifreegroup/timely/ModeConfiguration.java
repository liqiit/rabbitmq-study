package com.ifreegroup.timely;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
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
public class ModeConfiguration {

    @Bean
    public Exchange pullExchange(){
        return ExchangeBuilder.directExchange("pullExchange").durable(true).build();
    }
    @Bean
    public Queue pullQueue(){
        return QueueBuilder.durable("pullQueue").build();
    }
    @Bean
    public Binding pullBinding(){
        return BindingBuilder.bind(pullQueue()).to(pullExchange()).with("pull").noargs();
    }

    @Bean
    public Exchange pushExchange(){
        return ExchangeBuilder.directExchange("pushExchange").durable(true).build();
    }
    @Bean
    public Queue pushQueue(){
        return QueueBuilder.durable("pushQueue").build();
    }
    @Bean
    public Binding pushBinding(){
        return BindingBuilder.bind(pushQueue()).to(pushExchange()).with("push").noargs();
    }
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate=new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);
        return rabbitTemplate;
    }


}
