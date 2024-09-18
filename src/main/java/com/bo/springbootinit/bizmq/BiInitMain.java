package com.bo.springbootinit.bizmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;

/**
 * 用于创建测试程序用到的交换机和队列（只用在程序启动前执行一次）
 */


@Component
public class BiInitMain {
    @PostConstruct
    public void init() {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            String EXCHANGE_NAME = "your_exchange";
            String queueName = "your_queue";
            // 检查并声明交换机
            try {
                channel.exchangeDeclare(EXCHANGE_NAME, "direct", true);
            } catch (Exception e) {
                System.out.println("交换机声明时出现问题: " + e.getMessage());
            }
            // 检查并声明队列
            try {
                channel.queueDeclarePassive(queueName);
            } catch (Exception e) {
                // 如果队列不存在则创建
                channel.queueDeclare(queueName, true, false, false, null);
            }
            // 绑定队列和交换机
            channel.queueBind(queueName, EXCHANGE_NAME, "your_routing_key");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
