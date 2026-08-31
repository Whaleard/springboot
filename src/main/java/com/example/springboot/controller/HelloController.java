package com.example.springboot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @RestController注解的核心作用是将类标记为控制器，使方法返回值直接写入http响应体（通常为json数据），而非视图页面。
 * ‌它是Spring4.0引入的组合注解，等同于同时使用@Controller和@ResponseBody。
 */
@RestController
public class HelloController {

    @RequestMapping("/hello")
    public String helloHandle() {
        return "Hello Spring Boot!";
    }
}
