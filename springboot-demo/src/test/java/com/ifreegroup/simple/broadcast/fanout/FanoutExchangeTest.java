package com.ifreegroup.simple.broadcast.fanout;

import com.ifreegroup.RabbitMQApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Title: FanoutExchangeTest
 * Description: 扇出交换机测试类 忽略bindkey 所有接收者都会收到消息
 * Company: iFree Group
 *
 * @author liqi
 * @date 2020/12/2
 */
@SpringBootTest(classes = RabbitMQApplication.class)
public class FanoutExchangeTest {
    @Autowired
    private Sender sender;
    @Test
    public void fanoutExchangeTest(){
        sender.publish("fanout1");
    }
}
