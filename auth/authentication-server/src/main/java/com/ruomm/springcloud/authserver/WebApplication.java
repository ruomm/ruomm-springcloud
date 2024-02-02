package com.ruomm.springcloud.authserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;
@ConfigurationPropertiesScan
@SpringBootApplication
//@EnableJpaRepositories
@EnableScheduling
@MapperScan(basePackages = {"com.ruomm.springcloud.authserver.dao"})
public class WebApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }

}
