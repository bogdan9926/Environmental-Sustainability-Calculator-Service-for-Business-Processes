package com.thesis.camundaintegration.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class RabbitService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMessage(String channel, String message) {
        rabbitTemplate.convertAndSend(channel, message);
    }

}
