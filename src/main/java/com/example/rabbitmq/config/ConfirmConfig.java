package com.example.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfirmConfig {

    /**
     * 交换机
     */
    public static final String CONFIRM_EXCHANGE_NAME = "confirm_exchange";

    /**
     * 队列
     */
    public static final String CONFIRM_QUEUE_NAME = "confirm_queue";

    /**
     * 路由键
     */
    public static final String CONFIRM_ROUTING_KEY = "confirm_routing_key";

    /**
     * 声明交换机
     * @return
     */
    @Bean
    public DirectExchange confirmExchange() {
        return new DirectExchange(CONFIRM_EXCHANGE_NAME);
    }

    /**
     * 声明队列
     * @return
     */
    @Bean
    public Queue confirmQueue() {
        return new Queue(CONFIRM_QUEUE_NAME);
    }

    /**
     * 队列绑定交换机
     * @param confirmQueue
     * @param confirmExchange
     * @return
     */
    @Bean
    public Binding queueBindingExchange(Queue confirmQueue, DirectExchange confirmExchange) {
        return BindingBuilder.bind(confirmQueue).to(confirmExchange).with(CONFIRM_ROUTING_KEY);
    }
}
