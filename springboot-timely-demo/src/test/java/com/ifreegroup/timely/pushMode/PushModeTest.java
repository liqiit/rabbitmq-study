package com.ifreegroup.timely.pushMode;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Title: PushModeTest
 * Description:
 * Company: iFree Group
 *
 * @author liqi
 * @date 2020/12/10
 */
@SpringBootTest
public class PushModeTest {
    @Autowired
    private Publisher publisher;

    @Test
    public void testPush() {
        publisher.pushPublish();
    }
}
