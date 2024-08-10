package com.thesis.jbpmintegration.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thesis.jbpmintegration.domain.JbpmJson;
import com.thesis.jbpmintegration.service.RabbitService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.MalformedURLException;

@Component
public class RabbitMQListener {
    @Autowired
    private RabbitService rabbitService;
    @RabbitListener(queues = "jbpmChannel")
    public void receiveMessage(String message) throws JsonProcessingException, MalformedURLException {
        System.out.println("Received message from queue: " + message);
        ObjectMapper objectMapper = new ObjectMapper();
        JbpmJson json = objectMapper.readValue(message, JbpmJson.class);
        rabbitService.createContainer(json);
        rabbitService.startProcess(json);

    }
}

