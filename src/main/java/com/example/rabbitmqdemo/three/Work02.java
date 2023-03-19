package com.example.rabbitmqdemo.three;

import com.example.rabbitmqdemo.utils.RabbitMqUtils;
import com.example.rabbitmqdemo.utils.SleepUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 消息在手动应答时不丢失，放回队列中重新消费
 */
public class Work02 {
    //队列名称
    public static final String TASK_QUEUE_NAME = "ack_queue";

    //接收消息
    public static void main(String[] args) throws IOException, TimeoutException {
        Channel channel = RabbitMqUtils.getChannel();
        System.out.println("C1等待接收消息时间较短");
        //消息的接收
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            //沉睡1S
            SleepUtils.sleep(1);
            System.out.println("接收消息：" + new String(message.getBody(), "UTF-8"));
            //手动应答
            /**
             * 1.消息的标记 tag
             * 2.是否批量应答 false：不批量应答信道中的消息 true:批量
             */
            channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
        };
        //设置不公平分发
//        int prefectchCount = 1;
        //预取值是2
        int prefectchCount = 2;
        channel.basicQos(prefectchCount);
        //采用手动应答
        boolean autoAck = false;
        channel.basicConsume(TASK_QUEUE_NAME, autoAck, deliverCallback, (consumerTag) -> {
            System.out.println("消息者取消消费接口回调逻辑");
        });
    }
}
