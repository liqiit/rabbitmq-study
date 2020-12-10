package com.ifreegroup.reliability.confirm.publisher;

import com.ifreegroup.RabbitMQApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Title: PublisherAckTest
 * Description:
 * Company: iFree Group
 *
 * @author liqi
 * @date 2020/12/8
 */
@SpringBootTest(classes = {RabbitMQApplication.class})
public class PublisherAckTest {
    @Autowired
    private Publisher publisher;
    @Test
    public void testPublishAck() {
        //publisher.publish();
    }
}
