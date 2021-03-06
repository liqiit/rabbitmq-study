package com.ifreegroup.timely.pullMode;

import com.ifreegroup.timely.RabbitMQApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Title: PullModeTest
 * Description:
 * Company: iFree Group
 *
 * @author liqi
 * @date 2020/12/10
 */
@SpringBootTest(classes = {RabbitMQApplication.class})
public class PullModeTest {
    @Autowired
    private Publisher publisher;

    @Test
    public void testPull() {
        publisher.pullPublish();
    }
}
