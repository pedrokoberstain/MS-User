package com.compassuol.challenge3.User.config;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfig {

    @Value("${mq.queues.notification}")
    private String queueEmissaoNotificationFila;

    @Bean
    public Queue queueEmissaoNotification() {
        return new Queue(queueEmissaoNotificationFila, true);
    }
}
