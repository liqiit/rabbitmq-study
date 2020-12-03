package com.ifreegroup.simple.broadcast.fanout;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Title: FanoutConfiguration
 * Description:扇出交换机配置
 * Company: iFree Group
 *
 * @author liqi
 * @date 2020/12/2
 */
@Configuration
public class FanoutConfiguration {
    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange("fanoutExchange");
    }
}
