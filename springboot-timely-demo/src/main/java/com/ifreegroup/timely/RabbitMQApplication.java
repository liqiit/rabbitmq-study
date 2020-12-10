package com.ifreegroup.timely;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Title: RabbitMQApplication
 * Description:
 * Company: iFree Group
 *
 * @author liqi
 * @date 2020/12/10
 */
@SpringBootApplication
@EnableScheduling
public class RabbitMQApplication {
    public static void main(String[] args) {
        SpringApplication.run(RabbitMQApplication.class);
    }
}
