package com.ifreegroup.simple.peer2peer;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Title: P2PConfiguration
 * Description:
 * Company: iFree Group
 *
 * @author liqi
 * @date 2020/12/2
 */
@Configuration
public class P2PConfiguration {
    @Bean
    @Qualifier("hello")
    public Queue hello() {
        return new Queue("hello");
    }

    @Bean
    public Receiver receiver() {
        return new Receiver();
    }

    @Bean
    public Sender sender() {
        return new Sender();
    }
}
