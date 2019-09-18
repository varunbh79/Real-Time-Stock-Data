package com.boot.project.stocks.Configurations;

import com.boot.project.stocks.MessagingSystem.RabbitMQListener;
import com.boot.project.stocks.Services.RabbitMqServiceProvider;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RabbitMQConfiguration {

    private RabbitMqServiceProvider serviceProvider;

    public RabbitMQConfiguration (RabbitMqServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    @Bean
    ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("localhost");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        return connectionFactory;
    }

    @Bean
    MessageListenerContainer messageListenerContainer () {
        SimpleMessageListenerContainer messageListenerContainer = new SimpleMessageListenerContainer();
        messageListenerContainer.setConnectionFactory(connectionFactory());
        messageListenerContainer.setQueueNames(serviceProvider.retrieveQueueNamesAsync ());
        messageListenerContainer.setMessageListener(new RabbitMQListener());
        return messageListenerContainer;
    }

    @Bean
    RestTemplate restTemplate(){
        return new RestTemplate();
    }

}
