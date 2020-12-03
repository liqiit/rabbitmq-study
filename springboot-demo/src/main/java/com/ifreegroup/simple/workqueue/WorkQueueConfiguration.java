package com.ifreegroup.simple.workqueue;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Title: WorkQueueConfiguration
 * Description:
 * Company: iFree Group
 *
 * @author liqi
 * @date 2020/12/2
 */
@Configuration
public class WorkQueueConfiguration {
    @Bean
    @Qualifier("workqueue")
    public Queue workqueue() {
        return new Queue("workqueue");
    }


    @Bean
    public Sender workQueueSender(){
        return new Sender();
    }
}
