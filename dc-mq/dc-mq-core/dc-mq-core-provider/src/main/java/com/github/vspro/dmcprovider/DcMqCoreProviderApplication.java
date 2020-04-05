package com.github.vspro.dmcprovider;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/**
 * 当指定了@ComponentScan,当前工程的包也需要再次扫描一下，不然启动后访问不了url
 */
@SpringBootApplication
@EnableEurekaClient
@EnableTransactionManagement
@MapperScan(basePackages = {"com.github.vspro.**.persist.**.dao", "com.github.vspro.**.persist.**.mapper"})
@ComponentScan(basePackages = {"com.github.vspro.dmcprovider","com.github.vspro.**.svr","com.github.vspro.**.persist"})
public class DcMqCoreProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(DcMqCoreProviderApplication.class, args);
    }

}
