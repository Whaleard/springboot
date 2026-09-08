package com.example.rabbitmq.consumer;

import com.example.rabbitmq.util.TimeUtil;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 死信队列消费者
 */
@Slf4j
@Component
public class DeadLetterQueueConsumer {

    @RabbitListener(queues = "QD")
    public void receiveD(Message message, Channel channel) {
        String msg = new String(message.getBody());

        log.info("当前时间：{}，接收到死信队列消息：{}", TimeUtil.getCurrentTimeToString(), msg);
    }
}
