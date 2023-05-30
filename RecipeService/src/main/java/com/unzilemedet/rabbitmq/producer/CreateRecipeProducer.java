package com.unzilemedet.rabbitmq.producer;


import com.unzilemedet.rabbitmq.model.CreateRecipeModel;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateRecipeProducer {
    @Value("${rabbitmq.exchange}")
    private String exchange;
    @Value("${rabbitmq.bindingKeyCreateRecipe}")
    private String createRecipeBindingKey;

    private final RabbitTemplate rabbitTemplate;
    public Object createRecipe(CreateRecipeModel model){
        return rabbitTemplate.convertSendAndReceive(exchange, createRecipeBindingKey, model);
    }
}
