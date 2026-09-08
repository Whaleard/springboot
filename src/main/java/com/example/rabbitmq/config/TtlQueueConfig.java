package com.example.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * TTL队列配置文件类
 */
@Configuration
public class TtlQueueConfig {

    /**
     * 普通交换机名称
     */
    public static final String X_EXCHANGE = "X";

    /**
     * 死信交换机名称
     */
    public static final String Y_DEAD_LETTER_EXCHANGE = "Y";

    /**
     * 普通队列名称
     */
    public static final String X_QUEUE_A = "QA";
    public static final String X_QUEUE_B = "QB";

    /**
     * 死信队列名称
     */
    public static final String Y_DEAD_LETTER_QUEUE = "QD";

    /**
     * 声明普通交换机
     * @return
     */
    @Bean("xExchange")
    public DirectExchange xExchange() {
        return new DirectExchange(X_EXCHANGE);
    }

    /**
     * 声明死信交换机
     * @return
     */
    @Bean("yExchange")
    public DirectExchange yExchange() {
        return new DirectExchange(Y_DEAD_LETTER_EXCHANGE);
    }

    /**
     * 声明普通队列QA
     * @return
     */
    @Bean("queueA")
    public Queue QueueA() {
        return QueueBuilder
                .durable(X_QUEUE_A)
                // 设置死信交换机
                .withArgument("x-dead-letter-exchange", Y_DEAD_LETTER_EXCHANGE)
                // 设置死信路由键
                .withArgument("x-dead-letter-routing-key", "YD")
                // 设置消息过期时间
                .withArgument("x-message-ttl", 10000)
                .build();
    }

    /**
     * 声明普通队列QB
     * @return
     */
    @Bean("queueB")
    public Queue queueB() {
        return QueueBuilder
                .durable(X_QUEUE_B)
                // 设置死信交换机
                .withArgument("x-dead-letter-exchange", Y_DEAD_LETTER_EXCHANGE)
                // 设置死信路由键
                .withArgument("x-dead-letter-routing-key", "YD")
                // 设置消息过期时间
                .withArgument("x-message-ttl", 40000)
                .build();
    }

    /**
     * 声明死信队列
     * @return
     */
    @Bean("queueD")
    public Queue queueD() {
        return QueueBuilder
                .durable(Y_DEAD_LETTER_QUEUE)
                .build();
    }

    /**
     * 将队列QA绑定到交换机X
     */
    @Bean
    public Binding queueABindingX(Queue queueA, DirectExchange xExchange) {
        return BindingBuilder
                .bind(queueA)
                .to(xExchange)
                .with("XA");
    }

    /**
     * 将队列QB绑定到交换机X
     */
    @Bean
    public Binding queueBBindingX(Queue queueB, DirectExchange xExchange) {
        return BindingBuilder
                .bind(queueB)
                .to(xExchange)
                .with("XB");
    }

    /**
     * 将队列QD绑定到交换机Y
     */
    @Bean
    public Binding queueDBindingY(Queue queueD, DirectExchange yExchange) {
        return BindingBuilder
                .bind(queueD)
                .to(yExchange)
                .with("YD");
    }
}
