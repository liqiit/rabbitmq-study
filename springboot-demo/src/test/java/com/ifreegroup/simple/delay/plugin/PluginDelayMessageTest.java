package com.ifreegroup.simple.delay.plugin;

import com.ifreegroup.RabbitMQApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Title: PluginDelayMessageTest
 * Description:
 * Company: iFree Group
 *
 * @author liqi
 * @date 2020/12/3
 */
@SpringBootTest(classes = {RabbitMQApplication.class})
public class PluginDelayMessageTest {
    @Autowired
    private DelayedSender delayedSender;

    @Test
    public void delayMessageTest() {
        delayedSender.send();
    }
}
