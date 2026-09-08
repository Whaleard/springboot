package com.example.rabbitmq.controller;

import com.example.rabbitmq.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * 发送延迟消息
 */
@Slf4j
@RestController
@RequestMapping("/ttl")
public class SendMessage {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RequestMapping("/sendMsg")
    public void sendMsg(@RequestParam("message") String message) {
        log.info("当前时间：{}，发送一条消息给两个TTL队列：{}", TimeUtil.getCurrentTimeToString(), message);

        rabbitTemplate.convertAndSend("X", "XA", "消息来自ttl为10s的队列" + message);
        rabbitTemplate.convertAndSend("X", "XB", "消息来自ttl为40s的队列" + message);
    }
}
