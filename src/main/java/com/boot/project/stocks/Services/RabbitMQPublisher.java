package com.boot.project.stocks.Services;

import com.boot.project.stocks.Models.SimpleMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;



@Service
public class RabbitMQPublisher {

    private RabbitTemplate rabbitTemplate;

    public RabbitMQPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
    }

    @Scheduled(fixedDelay = 3000)
    public void publishMessage()  {
        SimpleMessage simpleMessage = new SimpleMessage();
        simpleMessage.setName("Spring Message");
        simpleMessage.setDescription("Message generated during startup");
        rabbitTemplate.convertAndSend("Test_Exchange","testRouting",simpleMessage);
    }
}
