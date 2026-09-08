package com.example.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 延时队列配置类
 */
@Configuration
public class DelayedQueueConfig {

    /**
     * 延时交换机
     */
    public static final String DELAYED_EXCHANGE_NAME = "delayed.exchange";

    /**
     * 延时队列
     */
    public static final String DELAYED_QUEUE_NAME = "delayed.queue";

    /**
     * 延时队列路由
     */
    public static final String DELAYED_ROUTING_KEY = "delayed.routingkey";

    /**
     * 声明延时交换机（基于插件）
     *
     * rabbitmq_delayed_message_exchange插件工作原理
     *  生产者发消息时在消息头带上x-delay参数（单位毫秒），消息不会立刻进队列，而是由延迟交换机内部暂存，等延迟时间到了再自动路由到目标队列。
     *  简单说就是声明一个x-delayed-message类型的交换机，发消息时在header里带上x-delay（单位毫秒），消息就会在指定时间后才被投递到队列消费‌。常用于订单超时关闭、定时任务这种场景。‌
     *
     * @return
     */
    @Bean
    public CustomExchange delayedExchange() {
        // 声明延时交换机必须设置x-delayed-type参数指定底层路由类型，如direct、topic、fanout、headers
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-delayed-type", "direct");
        // x-delayed-message是RabbitMQ官方延迟消息插件提供的自定义交换机类型，核心作用就是让消息延迟指定时间后再投递到队列。
        return new CustomExchange(DELAYED_EXCHANGE_NAME, "x-delayed-message", true, false, arguments);
    }

    /**
     * 声明队列
     * @return
     */
    @Bean
    public Queue delayedQueue() {
        return new Queue(DELAYED_QUEUE_NAME);
    }

    /**
     * 将队列绑定到延时交换机
     * @param delayedQueue
     * @param delayedExchange
     * @return
     */
    @Bean
    public Binding delayedQueueBindingDelayedExchange(Queue delayedQueue, Exchange delayedExchange) {
        return BindingBuilder.bind(delayedQueue).to(delayedExchange).with(DELAYED_ROUTING_KEY).noargs();
    }
}
