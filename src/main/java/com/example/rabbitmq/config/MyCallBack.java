package com.example.rabbitmq.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class MyCallBack implements RabbitTemplate.ConfirmCallback {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void init() {
        rabbitTemplate.setConfirmCallback(this);
    }

    /**
     * 交换机确认回调方法
     *  1、发消息，交换机接收消息后回调
     *      correlationData：保存回调消息的ID及相关信息
     *      ack：消息是否成功接收，值为true
     *      cause：失败原因，值为null
     *  2、发消息，交换机没接收到消息后回调
     *      correlationData：保存回调消息的ID及相关信息
     *      ack：消息是否成功接收，值为false
     *      cause：失败原因，值不为null
     *
     * @param correlationData
     * @param ack
     * @param cause
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        String id = correlationData != null ? correlationData.getId() : "";
        if (ack) {
            log.info("交换机已经收到id为{}的消息", id);
        } else {
            log.info("id为{}的消息发送失败，cause:{}", id, cause);
        }
    }
}
