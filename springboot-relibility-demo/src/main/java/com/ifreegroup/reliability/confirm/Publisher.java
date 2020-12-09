package com.ifreegroup.reliability.confirm;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ShutdownSignalException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.connection.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Title: Sender
 * Description:
 * Company: iFree Group
 *
 * @author liqi
 * @date 2020/12/2
 */
@Slf4j
@Component(value = "publishAckSender")
public class Publisher {
    final RabbitTemplate.ConfirmCallback confirmCallback = (correlationData, ack, cause) -> {
        log.info("确认结果：" + ack + "，原因：" + cause);
    };
    /**
     * 匹配发送到exchange但是没有关联指定的queue的消息
     */
    final RabbitTemplate.ReturnCallback returnCallback = (message, replyCode, replyText, exchange, routingKey) ->
            log.error("return exchange: " + exchange + ", routingKey: "
                    + routingKey + ", replyCode: " + replyCode + ", replyText: " + replyText);
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private DirectExchange publishAckExchange;

    /***
     * 增加returncallback和confirmcallback
     */
    public void publish() {
        //BindingBuilder.bind(publishAckQueue()).to(publishAckExchange()).with("publishAck");
        rabbitTemplate.setReturnCallback(returnCallback);
        rabbitTemplate.setConfirmCallback(confirmCallback);
        rabbitTemplate.convertAndSend(publishAckExchange.getName(), "publishAck", "publishAck message ");

    }

    /***
     * 如果发送的消息没有匹配的exchange,消息将会删除且channel异常关闭
     */
    public void publishUnkownExchange() {
        rabbitTemplate.setReturnCallback(returnCallback);
        rabbitTemplate.setConfirmCallback(confirmCallback);
        //
        rabbitTemplate.getConnectionFactory().addConnectionListener(new ConnectionListener() {
            @Override
            public void onCreate(Connection connection) {

            }

            @Override
            public void onClose(Connection connection) {
                System.out.println("connection close");
            }

            @Override
            public void onShutDown(ShutdownSignalException signal) {
                System.out.println(signal);

            }
        });
        ((CachingConnectionFactory) rabbitTemplate.getConnectionFactory()).addChannelListener(new ChannelListener() {
            @Override
            public void onCreate(Channel channel, boolean transactional) {

            }

            @Override
            public void onShutDown(ShutdownSignalException signal) {
                System.out.println(signal);
            }
        });

        rabbitTemplate.convertAndSend("aabbccdd", "publishAck", "publishAck message ");

    }

    /**
     * 异步等待结果
     * 通过{@link CorrelationData}进行替代callback进行回调
     *
     */
    public void publishFuture() {
        //BindingBuilder.bind(publishAckQueue()).to(publishAckExchange()).with("publishAck");
        CorrelationData correlationData = new CorrelationData();
        rabbitTemplate.convertAndSend(publishAckExchange.getName(), "publishAck", "publishAck message ", correlationData);
        try {
            correlationData.getFuture().get(10, TimeUnit.SECONDS).isAck();
            System.out.println(correlationData.getFuture().get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }

    /***
     * 同步等待结果
     */
    public void publishForWaitConfirm() {
        this.rabbitTemplate.invoke(operations -> {
            operations.convertAndSend(publishAckExchange.getName(), "publishAck", "publishAck message ");
            boolean result = operations.waitForConfirms(10000);
            if (result) {
                System.out.println("结果确认：" + result);
                return true;
            }
            return false;
        });
    }
}
