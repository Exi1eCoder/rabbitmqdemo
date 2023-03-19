package com.example.rabbitmqdemo.seven;

import com.example.rabbitmqdemo.utils.RabbitMqUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 声明主题交换机及相关队列
 *
 * 消费者C2
 */
public class ReceiveLogsTopic02 {
    //交换机的名称
    public static final String EXCHANGE_NAME = "topic_logs";

    //接收消息
    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitMqUtils.getChannel();
        //声明交换机
        channel.exchangeDeclare(EXCHANGE_NAME, "topic");
        //声明队列
        String queueName = "Q2";
        channel.queueDeclare(queueName, false, false, false, null);
        channel.queueBind(queueName,EXCHANGE_NAME, "*.*.rabbit");
        channel.queueBind(queueName,EXCHANGE_NAME, "lazy.#");
        System.out.println("等待接收消息......");
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            System.out.println(new String(message.getBody(), "UTF-8"));
            System.out.println("接收队列：" + queueName + " 绑定键： " + message.getEnvelope().getRoutingKey());
        };
        //接收消息
        channel.basicConsume(queueName, true, deliverCallback, consumerTag ->{});
    }
}
