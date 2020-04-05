package com.github.vspro.dccloudeureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class DcCloudEurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(DcCloudEurekaApplication.class, args);
    }

}
