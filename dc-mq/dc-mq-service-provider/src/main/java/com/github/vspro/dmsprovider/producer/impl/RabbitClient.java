package com.github.vspro.dmsprovider.producer.impl;

import com.github.vspro.dmsprovider.producer.MqClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * rabbitmq同步调用实现
 */
@Slf4j
public class RabbitClient implements MqClient {

    private RabbitTemplate rabbitTemplate;

    public RabbitClient(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public boolean sendMessage(String msg) {
        try {
            rabbitTemplate.send(MessageBuilder.withBody(msg.getBytes()).build());
            return Boolean.TRUE;
        } catch (AmqpException e) {
            log.error(">>>> 消息发送到服务节点失败....", e);
            return Boolean.FALSE;
        }
    }


}
