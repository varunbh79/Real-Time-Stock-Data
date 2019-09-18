package com.boot.project.stocks.MessagingSystem;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


@Component
@PropertySource("classpath:messaging_queues.properties")
@ConfigurationProperties(prefix = "app")
public class AnnotationBasedMQListener {

    @RabbitListener(queues = {"#{'${app.queues.name}'.split(',')}"})
    public void receiveMQMessage (Message message) {
        System.out.println ( "Message received : " + new String ( message.getBody () ) );
    }
}
