package com.thesis.mainapp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thesis.mainapp.service.AppService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQListener {
    @Autowired
    private AppService appService;

    @RabbitListener(queues = {"camundaReturnChannelNew","jbpmReturnChannelNew"})
    public void receiveMessage(String message) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(message);
        appService.parseReceivedMessage(jsonNode.toPrettyString());
    }
}

