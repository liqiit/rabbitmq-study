package com.ifreegroup.reliability.transaction;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Title: Sender
 * Description:
 * Company: iFree Group
 *
 * @author liqi
 * @date 2020/12/2
 */
@Slf4j
@Component(value = "txSender")
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
    private DirectExchange txExchange;


    /***
     * 事务消息投递
     */
    @Transactional(transactionManager = "rabbitTransactionManager", rollbackFor = Exception.class)
    public void publishForTransaction() {
        this.rabbitTemplate.convertAndSend(txExchange.getName(), "tx", "publishAck message ");
    }
}
