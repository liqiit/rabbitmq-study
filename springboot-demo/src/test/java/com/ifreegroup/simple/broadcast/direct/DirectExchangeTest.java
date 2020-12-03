package com.ifreegroup.simple.broadcast.direct;

import com.ifreegroup.RabbitMQApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Title: DirectExchangeTest
 * Description:直连交换机测试,通过不同bindkey发给不同消费者，当前配置direct或者direct2
 * Company: iFree Group
 *
 * @author liqi
 * @date 2020/12/2
 */
@SpringBootTest(classes = RabbitMQApplication.class)
public class DirectExchangeTest {
    @Autowired
    private Sender sender;
    @Test
    public void directExchangeTest(){
        sender.publish("direct");
    }
}
