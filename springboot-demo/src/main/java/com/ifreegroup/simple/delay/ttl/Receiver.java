package com.ifreegroup.simple.delay.ttl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Title: Receiver
 * Description:
 * Company: iFree Group
 *
 * @author liqi
 * @date 2020/12/2
 */
@Slf4j
@Component(value = "delayExchangeReceiver")
@RabbitListener(
        bindings = @QueueBinding(
                value = @Queue(
                        value = "deadQueue",
                        autoDelete = "false",
                        exclusive = "false"
                ),
                exchange = @Exchange(
                        value = "deadExchange",
                        type = ExchangeTypes.DIRECT //默认为direct类型
                ),
                key = {"ttl"} //routekey 可以绑定多个
        )
)
public class Receiver {
    @RabbitHandler
    public void receive(String message) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date now = new Date();
        System.out.println("  [x] Received delay message :'" + message + "'");
        System.out.println("  [x] CurrentTime :" + sf.format(now));
    }
}
