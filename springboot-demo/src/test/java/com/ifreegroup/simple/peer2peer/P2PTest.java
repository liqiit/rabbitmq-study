package com.ifreegroup.simple.peer2peer;

import com.ifreegroup.RabbitMQApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Title: P2PTest
 * Description: 测试点对点消息的发送和接收
 * Company: iFree Group
 *
 * @author liqi
 * @date 2020/12/2
 */
@SpringBootTest(classes = {RabbitMQApplication.class})
public class P2PTest {
    @Autowired
    private Sender sender;
    @Test
    public void testP2PMessage(){
        sender.send();
    }
}
