package com.example.springboot.config;

import com.example.springboot.bean.Pet;
import com.example.springboot.bean.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * 1、配置类里面使用@Bean标注在方法上给容器注册组件，默认也是单实例的
 * 2、配置类本身也是组件
 *
 * @Configuration注解的作用是告诉SpringBoot这是一个配置类
 * proxyBeanMethods: 代理模式
 *      Full(proxyBeanMethods = true)
 *          1、配置类被代理，方法调用通过代理拦截。
 *          2、确保Bean单例，支持方法间调用的依赖关系，能处理部分循环依赖。
 *          3、增加代理生成开销，可能略微影响启动速度和内存占用。
 *          4、配置类组件之间有依赖关系，方法会被调用得到之前单实例组件，用Full模式。
 *      Lite(proxyBeanMethods = false)
 *          1、配置类不生成代理，@Bean方法直接调用。
 *          2、减少代理开销，提升启动速度，降低内存占用，适合性能敏感场景。
 *          3、方法间直接调用会创建新实例，破坏单例特性；无法通过配置类方法调用处理循环依赖。
 *          4、配置类组件之间无依赖关系，用Lite模式加速容器启动过程，减少判断。
 *
 * @Import注解的作用是给容器中自动创建出指定的组件，默认组件名称是全类名。
 */
@Import({User.class})
@Configuration(proxyBeanMethods = true)
public class MyConfig {

    /**
     * 外部无论对配置类中组件的注册方法调用多少次获取的都是之前注册容器中的单实例对象
     *
     * @Bean注解的作用是给容器中添加组件，以方法名作为组件的id，返回类型就是组件类型。返回的值就是组件在容器中的实例
     *
     * @return
     */
    @Bean
    public User initUser() {
        return new User("张三", 18);
    }

    @Bean("tom")
    public Pet initPet() {
        return new Pet("tomcat");
    }
}
