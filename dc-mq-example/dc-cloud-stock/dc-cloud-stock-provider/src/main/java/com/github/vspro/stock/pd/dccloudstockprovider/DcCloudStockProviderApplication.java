package com.github.vspro.stock.pd.dccloudstockprovider;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.github.vspro.**.api")
@EnableEurekaClient
@EnableTransactionManagement
@MapperScan(basePackages = {"com.github.vspro.**.persist.**.dao", "com.github.vspro.**.persist.**.mapper"})
@ComponentScan(basePackages = {"com.github.vspro.stock.pd","com.github.vspro.**.svr", "com.github.vspro.**.persist"})
public class DcCloudStockProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(DcCloudStockProviderApplication.class, args);
    }

}
