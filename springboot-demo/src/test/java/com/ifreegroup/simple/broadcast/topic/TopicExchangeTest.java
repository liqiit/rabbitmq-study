package com.ifreegroup.simple.broadcast.topic;

import com.ifreegroup.RabbitMQApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Title: TopicExchangeTest
 * Description:主题交换机测试，通过匹配规则进行过滤消息
 * Company: iFree Group
 *
 * @author liqi
 * @date 2020/12/2
 */
@SpringBootTest(classes = {RabbitMQApplication.class})
public class TopicExchangeTest {
    @Autowired
    private Sender sender;
    @Test
    public void topicExchangeTest(){
        //receiver3收到消息
        sender.publish("orange.speed.color.food.hello");
        //receiver3收不到消息
        //sender.publish("orange.speed.color.food");
    }

}
