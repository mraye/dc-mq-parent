package com.github.vspro.dmsprovider;

import com.github.vspro.dmsprovider.task.MessageTask;
import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.quartz.SimpleThreadPoolTaskExecutor;

import java.util.concurrent.CountDownLatch;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages = "com.github.vspro.**.api")
public class DcMqServiceProviderApplication {

    public static void main(String[] args) {
//        SpringApplication.run(DcMqServiceProviderApplication.class, args);

        SpringApplication application = new SpringApplication(DcMqServiceProviderApplication.class);
        ConfigurableApplicationContext context = application.run(args);
        try {
            MessageTask task = context.getBean(MessageTask.class);
            task.start();
            new CountDownLatch(1).await();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("启动失败!!");
        }
    }


    @Bean("mq-task-executor")
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        int core = Runtime.getRuntime().availableProcessors();
        executor.setCorePoolSize(core);
        executor.setMaxPoolSize(core * 2 + 1);
        executor.setKeepAliveSeconds(3);
        executor.setQueueCapacity(50);
        executor.setThreadNamePrefix("tx-mq");
        return executor;
    }

}
