package com.ifreegroup.simple.workqueue;

import com.ifreegroup.RabbitMQApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Title: WorkQueueTest
 * Description: 测试工作队列，所有接收者共享队列消息
 * Company: iFree Group
 *
 * @author liqi
 * @date 2020/12/2
 */
@SpringBootTest(classes = {RabbitMQApplication.class})
public class WorkQueueTest {
    @Autowired
    private Sender sender;

    @Test
    public void testWorkQueue() {
        sender.send();
    }
}
