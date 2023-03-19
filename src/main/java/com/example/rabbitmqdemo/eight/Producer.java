package com.example.rabbitmqdemo.eight;

import com.example.rabbitmqdemo.utils.RabbitMqUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 死信队列 之 生产者代码
 */
public class Producer {
    //普通交换机的名称
    public static final String NORMAL_EXCHANGE = "normal_exchange";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitMqUtils.getChannel();
        channel.exchangeDeclare(NORMAL_EXCHANGE, BuiltinExchangeType.DIRECT);
        //死信消息 设置TTL时间 单位是ms 10000ms = 10s
        // time to live
//        AMQP.BasicProperties properties = new AMQP.BasicProperties().builder().expiration("10000").build();
        for(int i = 1; i < 11; i++){
            String message = "info" + i;
            channel.basicPublish(NORMAL_EXCHANGE, "zhangsan", null, message.getBytes());
        }
    }
}
