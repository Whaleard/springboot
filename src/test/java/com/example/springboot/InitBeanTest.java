package com.example.springboot;

import com.example.springboot.bean.Pet;
import com.example.springboot.bean.User;
import com.example.springboot.config.MyConfig;
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

	/**
	 * 测试@Configuration注解的proxyBeanMethods属性
	 */
	@Test
	public void test02() {
		ConfigurableApplicationContext run = SpringApplication.run(SpringbootApplication.class);

		// 当proxyBeanMethods为true时，Spring会为配置类生成CGLIB代理对象，此处的bean即为MyConfig类的代理对象
		MyConfig bean = run.getBean(MyConfig.class);
		System.out.println(bean);

		// 调用配置类中被@Bean注解标记的方法会被代理对象拦截，不会创建新的Bean实例，而是返回已创建的实例
		User user = bean.initUser();
		User user2 = bean.initUser();
		System.out.println("user == user2: " + (user == user2));
	}

	/**
	 * 测试@ConditionalOnBean注解
	 */
	@Test
	public void test03() {
		ConfigurableApplicationContext run = SpringApplication.run(SpringbootApplication.class);

		boolean tom = run.containsBean("tom");
		System.out.println("容器中定义了tom组件：" + tom);
	}
}
