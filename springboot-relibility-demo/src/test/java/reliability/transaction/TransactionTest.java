package reliability.transaction;

import com.ifreegroup.reliability.RabbitMQApplication;
import com.ifreegroup.reliability.transaction.Publisher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Title: TransactionTest
 * Description:
 * Company: iFree Group
 *
 * @author liqi
 * @date 2020/12/9
 */
@SpringBootTest(classes = RabbitMQApplication.class)
public class TransactionTest {
    @Autowired
    private Publisher publisher;

    @Test
    public void testTransaction() {
        publisher.publishForTransaction();
    }
}
