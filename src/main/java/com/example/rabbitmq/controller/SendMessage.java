package com.example.rabbitmq.controller;

import com.example.rabbitmq.config.DelayedQueueConfig;
import com.example.rabbitmq.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 发送延迟消息
 */
@Slf4j
@RestController
@RequestMapping("/ttl")
public class SendMessage {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 过期时间设置在队列上，所有发送到队列的消息延迟时间都是该队列设定的固定值，而业务需求不同延迟时间不同且延迟时间随着重试次数线性增长，这样就需要创建很多个固定延迟时间的队列。
     *
     * @param message
     */
    @RequestMapping("/sendMsg")
    public void sendMsg(@RequestParam("message") String message) {
        log.info("当前时间：{}，发送一条消息给两个TTL队列：{}", TimeUtil.getCurrentTimeToString(), message);

        rabbitTemplate.convertAndSend("X", "XA", "消息来自ttl为10s的队列" + message);
        rabbitTemplate.convertAndSend("X", "XB", "消息来自ttl为40s的队列" + message);
    }

    /**
     * 过期时间设置在消息上，就会因为消息进入信道的时序问题形成队头阻塞现象。因为队列消息是按序消费的，如果队头的消息延迟时间是10s, 后面的消息都要等至少10s后才可以进行消费。
     * @param message
     * @param ttlTime
     */
    @RequestMapping("/sendExpirationMsg")
    public void sendExpirationMsg(@RequestParam("message") String message, @RequestParam("ttlTime") String ttlTime) {
        log.info("当前时间：{}，发送一条时长{}毫秒的TTL消息给队列QC：{}", TimeUtil.getCurrentTimeToString(), ttlTime, message);
        rabbitTemplate.convertAndSend("X", "XC", message, msg -> {
            // 发送消息设置TTL
            msg.getMessageProperties().setExpiration(ttlTime);
            return msg;
        });
    }

    /**
     * 延迟插件是将延迟时间设置在消息上，这样只要创建一个队列即可；
     * 指定为延迟类型的交换机在接收到消息后并未立即将消息投递至目标队列中，而是存储在mnesia（一个分布式数据系统）表中，检测消息延迟时间，在达到可投递时间时才投递至目标队列，这样就不存在队头阻塞现象。
     * @param message
     * @param delayTime
     */
    @RequestMapping("/sendDelayedMsg")
    public void sendDelayedMsg(@RequestParam("message") String message, @RequestParam("delayTime") Integer delayTime) {
        log.info("当前时间：{}，发送一条时长{}毫秒的延迟消息给队列：{}", TimeUtil.getCurrentTimeToString(), delayTime, message);
        rabbitTemplate.convertAndSend(DelayedQueueConfig.DELAYED_EXCHANGE_NAME, DelayedQueueConfig.DELAYED_ROUTING_KEY, message, msg -> {
            // 发送消息设置延迟时长
            // setDelay方法底层就是在消息头header里设置x-delay参数（单位毫秒）
            msg.getMessageProperties().setDelay(delayTime);
            return msg;
        });
    }
}
