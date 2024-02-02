package com.ruomm.springcloud.authserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
@ConfigurationPropertiesScan
@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.ruomm.springcloud.authserver.dao")
@EntityScan(basePackages = "com.ruomm.springcloud")
@EnableScheduling
public class WebAuthServer {

    public static void main(String[] args) {
        SpringApplication.run(WebAuthServer.class, args);
    }

}
