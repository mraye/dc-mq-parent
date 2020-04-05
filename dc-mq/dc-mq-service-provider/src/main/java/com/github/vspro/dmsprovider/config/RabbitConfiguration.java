package com.github.vspro.dmsprovider.config;

import com.github.vspro.dmsprovider.constants.ServiceConstants;
import com.github.vspro.dmsprovider.producer.MqClient;
import com.github.vspro.dmsprovider.producer.impl.RabbitClient;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfiguration {



    @Bean
    public ConnectionFactory connectionFactory(){
        return new CachingConnectionFactory("192.168.199.234");
    }


    @Bean
    public AmqpAdmin amqpAdmin(){
        return new RabbitAdmin(connectionFactory());
    }


    /**
     * 默认的rabbitmqTemplate中exchange 为"", 而queue也是为 "", route_key 也是为空！！
     *
     * 所以指定了queue或者exchange后， rabbitmqTemplate也要修改！！
     *
     * 不然生产者发送了，但是消费者接收不到消息
     * @return
     */
    @Bean
    public RabbitTemplate rabbitTemplate(){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        rabbitTemplate.setDefaultReceiveQueue(queue().getName());
        rabbitTemplate.setExchange(exchange().getName());
        rabbitTemplate.setRoutingKey(binding().getRoutingKey());
        return rabbitTemplate;
    }


    @Bean
    public Queue queue(){
        return new Queue(ServiceConstants.QUEUE_NAME);
    }

    @Bean
    public Exchange exchange(){
        return new DirectExchange(ServiceConstants.EXCHANGE_NAME, true, false);
    }

    @Bean
    public Binding binding(){
        return BindingBuilder.bind(queue()).to(exchange()).with(ServiceConstants.ROUTE_KEY).noargs();
    }


    @Bean
    public MqClient mqClient(){
        return new RabbitClient(rabbitTemplate());
    }

}
