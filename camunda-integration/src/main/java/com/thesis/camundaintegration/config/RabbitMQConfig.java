package com.thesis.camundaintegration.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
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
    Queue queue() {
        return new Queue("camundaChannel", true); // true for durable queue
    }

    @Bean
    Queue queue2() {
        return QueueBuilder.durable("camundaReturnChannelNew")
                .withArgument("x-dead-letter-exchange", "dlxExchange")
                .withArgument("x-dead-letter-routing-key", "camunda")
                .withArgument("x-message-ttl", 1000) // 1 second TTL
                .build();
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange("yourExchange");
    }

    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("routingKey");
    }

    @Bean
    Binding binding2(Queue queue2, TopicExchange exchange) {
        return BindingBuilder.bind(queue2).to(exchange).with("anotherRoutingKey");
    }
}
