package com.example.rabbitmq.consumer;

import com.example.rabbitmq.config.DelayedQueueConfig;
import com.example.rabbitmq.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 延时队列消费者（基于插件）
 */
@Slf4j
@Component
public class DelayedQueueConsumer {

    /**
     * 监听消息
     * @param message
     */
    @RabbitListener(queues = DelayedQueueConfig.DELAYED_QUEUE_NAME)
    public void receiveDelayedQueue(Message message) throws Exception {
        String msg = new String(message.getBody(), "UTF-8");
        log.info("当前时间：{}，接收到延时队列消息：{}", TimeUtil.getCurrentTimeToString(), msg);
    }
}
