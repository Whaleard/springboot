package com.example.rabbitmq.consumer;

import com.example.rabbitmq.config.ConfirmConfig;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ConfirmConsumer {

    @RabbitListener(queues = ConfirmConfig.CONFIRM_QUEUE_NAME)
    public void receiveConfirmMessage(Message message, Channel channel) throws Exception {
        String msg = new String(message.getBody(), "UTF-8");
        log.info("接收到消息：{}", msg);
    }
}
