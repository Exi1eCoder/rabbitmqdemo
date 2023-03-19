package com.example.rabbitmqdemo.two;

import com.example.rabbitmqdemo.utils.RabbitMqUtils;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 一个工作线程，相当于之前消费者
 */
public class Worker01 {
    //队列的名称
    public static final String QUEUE_NAME = "hello";

    //接收消息
    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitMqUtils.getChannel();

        //消息的接收
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            System.out.println("接收消息：" + new String(message.getBody()));
        };
        CancelCallback cancelCallback = (consumerTag) -> {
            System.out.println("消息者取消消费接口回调逻辑");
        };
        System.out.println("C2等待接收消息......");
        /**
         * 消费者消费消息
         * 1.消费哪个队列
         * 2.消费成功之后是否要自动应答 true代表的自动应答 false代表手动应答
         * 3.消费者未成功消费的回调
         * 4.消费者取消费的回调
         */
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, cancelCallback);
    }
}
