package com.unzilemedet.config.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    //create post producer işlemi için gerekli değişkenler
    @Value("${rabbitmq.exchange}")
    private String exchange;
    @Value("${rabbitmq.queueCreateRecipe}")
    private String queueRecipe;
    @Value("${rabbitmq.bindingKeyCreateRecipe}")
    private String createRecipeBindingKey;

    @Bean
    Queue queueRecipe(){
        return new Queue(queueRecipe);
    }
    @Bean
    DirectExchange exchange(){
        return new DirectExchange(exchange);
    }
    @Bean
    public Binding recipeBindingKey(final Queue queueRecipe, final DirectExchange exchange){
        return BindingBuilder.bind(queueRecipe).to(exchange).with(createRecipeBindingKey);
    }
}
