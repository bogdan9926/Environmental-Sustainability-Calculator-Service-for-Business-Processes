package com.thesis.mainapp.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMessage(String channel, String message) {
        System.out.println(channel + message);
        rabbitTemplate.convertAndSend(channel, message);
    }
}
