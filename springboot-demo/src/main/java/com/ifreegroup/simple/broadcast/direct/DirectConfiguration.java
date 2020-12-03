package com.ifreegroup.simple.broadcast.direct;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Title: DirectConfiguration
 * Description: 直连交换机配置
 * Company: iFree Group
 *
 * @author liqi
 * @date 2020/12/2
 */
@Configuration
public class DirectConfiguration {
    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange("directExchange");
    }
    @Bean
    public Queue autoDeleteQueue1(){
        return new AnonymousQueue();
    }
    @Bean
    public Queue autoDeleteQueue2(){
        return new AnonymousQueue();
    }
    @Bean
    public Binding binding1(DirectExchange directExchange,Queue autoDeleteQueue1){
        return BindingBuilder.bind(autoDeleteQueue1).to(directExchange).with("direct");
    }

    /***
     * 绑定交换机和队列，也可以在消费者中进行绑定
     * @param directExchange
     * @param autoDeleteQueue2
     * @return
     */
    @Bean
    public Binding binding2(DirectExchange directExchange,Queue autoDeleteQueue2){
        return BindingBuilder.bind(autoDeleteQueue2).to(directExchange).with("direct2");
    }
}
