package com.example.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 主程序类
 *
 * @SpringBootApplication注解是SpringBoot项目的核心启动注解，标注在主启动类上。
 * 它是一个组合注解，替代原本需要的三个注解，主要用来简化配置
 *  1、开启自动配置‌：内部包含@EnableAutoConfiguration，SpringBoot会根据引入的jar包自动配置数据源、Redis等Bean，无需手动编写大量配置文件。
 *  2、标记配置类‌：内部包含@SpringBootConfiguration，本质上是@Configuration，标识该类为Spring配置类，可以定义Bean。
 *  3、扫描组件‌：内部包含@ComponentScan，默认扫描启动类所在包及其子包下的@Component等组件并注册为Bean。‌
 * 注：使用时通常将启动类放在项目根包路径下，以便扫描到所有组件。
 */
@SpringBootApplication
public class SpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootApplication.class, args);
	}

}
