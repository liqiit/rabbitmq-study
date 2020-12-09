package reliability.confirm.publisher;

import com.ifreegroup.reliability.RabbitMQApplication;
import com.ifreegroup.reliability.confirm.Publisher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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
        publisher.publish();
    }

    @Test
    public void testPublishUnkownExchange() {
        publisher.publishUnkownExchange();
    }

    @Test
    public void testPublishFuture() {
        publisher.publishFuture();
    }

    @Test
    public void testPublishForWait() {
        publisher.publishForWaitConfirm();
    }
}
