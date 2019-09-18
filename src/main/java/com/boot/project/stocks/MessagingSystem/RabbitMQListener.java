package com.boot.project.stocks.MessagingSystem;




import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.annotation.RabbitListener;


public class RabbitMQListener implements MessageListener {


    @Override
    public void onMessage(Message message) {
        System.out.println("Received Message : " +new String(message.getBody()));
    }
}
