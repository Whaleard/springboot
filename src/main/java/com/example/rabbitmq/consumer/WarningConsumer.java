package com.example.rabbitmq.consumer;

import com.example.rabbitmq.config.ConfirmConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

@Slf4j
@Component
public class WarningConsumer {

    /**
     * 接收报警消息
     * @param message
     */
    @RabbitListener(queues = ConfirmConfig.WARNING_QUEUE_NAME)
    public void receiveWarningMsg(Message message) throws Exception {
        String msg = new String(message.getBody(), "UTF-8");
        log.info("报警发现不可路由消息：{}", msg);
    }
}
