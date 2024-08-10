package com.thesis.mainapp.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static org.mockito.Mockito.*;

public class RabbitServiceTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private RabbitService rabbitService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSendMessage() {
        String channel = "testChannel";
        String message = "testMessage";

        rabbitService.sendMessage(channel, message);

        verify(rabbitTemplate, times(1)).convertAndSend(channel, message);
    }
}
