package com.thesis.mainapp.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${spring.rabbitmq.host}")
    private String rabbitMQHost;

    @Value("${spring.rabbitmq.username}")
    private String rabbitMQUsername;

    @Value("${spring.rabbitmq.password}")
    private String rabbitMQPassWord;

    @Bean
    public CachingConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(rabbitMQHost);
        connectionFactory.setUsername(rabbitMQUsername);
        connectionFactory.setPassword(rabbitMQPassWord);
        return connectionFactory;
    }

    @Bean
    public Queue dlqCamunda() {
        return new Queue("camundaDLQ", true);
    }

    @Bean
    public Queue dlqJBPM() {
        return new Queue("jbpmDLQ", true);
    }

    @Bean
    public DirectExchange dlxExchange() {
        return new DirectExchange("dlxExchange");
    }

    @Bean
    public Binding dlqCamundaBinding() {
        return BindingBuilder.bind(dlqCamunda()).to(dlxExchange()).with("camunda");
    }

    @Bean
    public Binding dlqJBPMBinding() {
        return BindingBuilder.bind(dlqJBPM()).to(dlxExchange()).with("jbpm");
    }

    @Bean
    public Queue queueCamunda() {
        return QueueBuilder.durable("camundaReturnChannelNew") // Use a new queue name
                .withArgument("x-dead-letter-exchange", "dlxExchange")
                .withArgument("x-dead-letter-routing-key", "camunda")
                .withArgument("x-message-ttl", 1000) // 1 second TTL
                .build();
    }

    @Bean
    public Queue queueJBPM() {
        return QueueBuilder.durable("jbpmReturnChannelNew")
                .withArgument("x-dead-letter-exchange", "dlxExchange")
                .withArgument("x-dead-letter-routing-key", "jbpm")
                .withArgument("x-message-ttl", 1000) // 1 second TTL
                .build();
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange("mainExchange");
    }

    @Bean
    public Binding camundaBinding(Queue queueCamunda, DirectExchange exchange) {
        return BindingBuilder.bind(queueCamunda).to(exchange).with("camundaKey");
    }

    @Bean
    public Binding jbpmBinding(Queue queueJBPM, DirectExchange exchange) {
        return BindingBuilder.bind(queueJBPM).to(exchange).with("jbpmKey");
    }
}
