package com.ifreegroup.simple.delay.plugin;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * Title: DelayedConfig
 * Description:
 * Company: iFree Group
 *
 * @author liqi
 * @date 2020/12/2
 */
@Configuration
public class DelayedConfig {

    @Bean("pluginDelayQueue")
    public Queue queue() {
        return new Queue("pluginDelayQueue");
    }

    // 配置默认的交换机
    @Bean("pluginDelayExchange")
    CustomExchange customExchange() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-delayed-type", "direct");
        //参数二为类型：必须是x-delayed-message
        return new CustomExchange("pluginDelayExchange", "x-delayed-message", true, false, args);
    }
    // 绑定队列到交换器
    @Bean("pluginDelayBing")
    Binding binding(@Qualifier("pluginDelayQueue") Queue queue, @Qualifier("pluginDelayExchange") CustomExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("pluginDelayQueue").noargs();
    }
}
