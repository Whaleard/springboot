package com.example.springboot.bean;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @ConfigurationProperties注解的作用是将application.properties文件中的配置项绑定到类的属性上，前提是该类必须被Spring管理，可以在该类上添加@Component注解或者在配置类上添加@ConfigurationProperties注解。
 */
@ConfigurationProperties(prefix = "car")
public class Car {

    private String brand;

    private String price;

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Car{" +
                "brand='" + brand + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
