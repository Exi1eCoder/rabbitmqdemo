package com.example.rabbitmqdemo.eight;

import com.example.rabbitmqdemo.utils.RabbitMqUtils;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 死信队列实战
 *
 * 消费者 02
 */
public class Consumer02 {
    public static final String DEAD_EXCHANGE = "dead_exchange";
    public static final String DEAD_QUEUE = "dead_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitMqUtils.getChannel();
        //声明死信和普通交换机类型为direct
        channel.exchangeDeclare(DEAD_EXCHANGE, BuiltinExchangeType.DIRECT);
        channel.queueDeclare(DEAD_QUEUE,false, false, false,null);
        channel.queueBind(DEAD_QUEUE, DEAD_EXCHANGE, "lisi");
        System.out.println("等待接收死信队列消息......");
        DeliverCallback deliverCallback = (consumerTag, message) ->{
            System.out.println("Consumer02接收的消息是：" + new String(message.getBody(),"UTF-8"));
        };
        channel.basicConsume(DEAD_QUEUE, true, deliverCallback, consumerTag -> {});
    }
}
