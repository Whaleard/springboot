package com.example.rabbitmq.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class MyCallBack implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void init() {
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnCallback(this);
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

    /**
     * 捕获消息从交换机路由到队列失败的回调方法
     * @param message
     * @param replyCode
     * @param replyText
     * @param exchange
     * @param routingKey
     */
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        log.info("消息：{}，路由到队列失败，被交换机：{}退回，路由：{}，退回原因：{}", new String(message.getBody()), exchange, routingKey, replyText);
    }
}
