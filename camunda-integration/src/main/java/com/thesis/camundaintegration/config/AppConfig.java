package com.thesis.camundaintegration.config;


import io.camunda.zeebe.client.ZeebeClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

//    @Bean
//    public ZeebeClient zeebeClient() {
//        ZeebeClient client = ZeebeClient.newClientBuilder()
//                // Configure your client here
//                .build();
//
//        Runtime.getRuntime().addShutdownHook(new Thread(client::close));
//        return client;
//    }
}
