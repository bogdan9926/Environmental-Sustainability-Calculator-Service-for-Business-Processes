package com.thesis.jbpmintegration.controller;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class RabbitMQListener {

    @RabbitListener(queues = "jbpmChannel")
    public void receiveMessage(String message) {
        System.out.println("Received message from queue: " + message);
        File file = new File(message);
    }
}

