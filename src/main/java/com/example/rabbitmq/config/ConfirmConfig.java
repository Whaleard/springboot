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
     * 备份交换机
     */
    public static final String BACKUP_EXCHANGE_NAME = "backup_exchange";

    /**
     * 备份队列
     */
    public static final String BACKUP_QUEUE_NAME = "backup_queue";

    /**
     * 报警队列
     */
    public static final String WARNING_QUEUE_NAME = "warning_queue";

    /**
     * 声明交换机
     * @return
     */
    @Bean
    public DirectExchange confirmExchange() {
        return ExchangeBuilder
                .directExchange(CONFIRM_EXCHANGE_NAME)
                .durable(true)
                // 设置备份交换机
                .withArgument("alternate-exchange", BACKUP_EXCHANGE_NAME)
                .build();
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

    /**
     * 声明备份交换机
     * @return
     */
    @Bean
    public FanoutExchange backupExchange() {
        return new FanoutExchange(BACKUP_EXCHANGE_NAME);
    }

    /**
     * 声明备份队列
     * @return
     */
    @Bean
    public Queue backupQueue() {
        return new Queue(BACKUP_QUEUE_NAME);
    }

    /**
     * 声明报警队列
     * @return
     */
    @Bean
    public Queue warningQueue() {
        return new Queue(WARNING_QUEUE_NAME);
    }

    /**
     * 备份队列绑定备份交换机
     * @param backupQueue
     * @param backupExchange
     * @return
     */
    @Bean
    public Binding backupQueueBindingBackupExchange(Queue backupQueue, FanoutExchange backupExchange) {
        return BindingBuilder.bind(backupQueue).to(backupExchange);
    }

    /**
     * 报警队列绑定备份交换机
     * @param warningQueue
     * @param backupExchange
     * @return
     */
    @Bean
    public Binding warningQueueBindingBackupExchange(Queue warningQueue, FanoutExchange backupExchange) {
        return BindingBuilder.bind(warningQueue).to(backupExchange);
    }
}
