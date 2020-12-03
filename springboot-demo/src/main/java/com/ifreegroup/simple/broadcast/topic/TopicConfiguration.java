package com.ifreegroup.simple.broadcast.topic;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Title: TopicConfiguration
 * Description: 主题交换机配置
 * Company: iFree Group
 *
 * @author liqi
 * @date 2020/12/2
 */
@Configuration
public class TopicConfiguration {
    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange("topicExchange");
    }
}
