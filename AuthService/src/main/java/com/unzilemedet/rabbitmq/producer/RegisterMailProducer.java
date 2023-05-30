package com.unzilemedet.rabbitmq.producer;

import com.unzilemedet.rabbitmq.model.RegisterMailModel;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterMailProducer {
    @Value("${rabbitmq.exchange-auth}")
    private String directExchange;
    @Value("${rabbitmq.registerMailBindingKey}")
    private String registerMailBindingKey;

    private final RabbitTemplate rabbitTemplate;

    public void sendActivationCode(RegisterMailModel registerMailModel){
        rabbitTemplate.convertAndSend(directExchange,registerMailBindingKey, registerMailModel);
    }
}
