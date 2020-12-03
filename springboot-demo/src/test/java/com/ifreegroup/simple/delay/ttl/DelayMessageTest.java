package com.ifreegroup.simple.delay.ttl;

import com.ifreegroup.RabbitMQApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Title: DelayMessageTest
 * Description:通过ttl+死信 测试延迟消息
 * Company: iFree Group
 *
 * @author liqi
 * @date 2020/12/2
 */
@SpringBootTest(classes = {RabbitMQApplication.class})
public class DelayMessageTest {
    @Autowired
    private Sender sender;

    @Test
    public void delayMessageTest() {
        sender.send();
    }

}
