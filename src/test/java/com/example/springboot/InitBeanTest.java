package com.example.springboot;

import com.example.springboot.bean.Pet;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootTest
class InitBeanTest {

	/**
	 * 测试IOC容器初始化过程，以及bean的创建过程
	 */
	@Test
	public void test01() {
		// 1、返回IOC容器
		ConfigurableApplicationContext run = SpringApplication.run(SpringbootApplication.class);

		// 2、查看容器里面的组件
		String[] names = run.getBeanDefinitionNames();
		for (String name : names) {
			System.out.println(name);
		}

		// 3、从容器中获取组件
		Pet tom = run.getBean("tom", Pet.class);
		Pet tom2 = run.getBean("tom", Pet.class);
		// Spring容器默认是单例模式
		System.out.println("tom == tom2: " + (tom == tom2));
	}
}
