package com.example.rabbitmqboot.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class DelayedQueueConfig {
    //队列
    public static final String DELAYED_QUEUE_NAME = "delayed.queue";
    //交换机
    public static final String DELAYED_EXCHANGE_NAME = "delayed.exchange";
    //routingKey
    public static final String DELAYED_ROUTING_KEY = "delayed.routingkey";

    @Bean
    public Queue delayedQueue(){
        return new Queue(DELAYED_QUEUE_NAME);
    }

    //声明交换机 基于插件的
    @Bean
    public CustomExchange delayedExchange(){
        Map<String ,Object> arguements = new HashMap<>();
        arguements.put("x-delayed-type", "direct");
        /**
         * 1.交换机的名称
         * 2.交换机的类型
         */
        return new CustomExchange(DELAYED_EXCHANGE_NAME, "x-delayed-message",true,false, arguements);
    }

    //绑定
    @Bean
    public Binding delayedQueueBindingDelayedExchange(
            @Qualifier("delayedQueue") Queue delayedQueue,
            @Qualifier("delayedExchange") CustomExchange delayedExchange
    ){
        return BindingBuilder.bind(delayedQueue).to(delayedExchange).with(DELAYED_ROUTING_KEY).noargs();
    }
}
