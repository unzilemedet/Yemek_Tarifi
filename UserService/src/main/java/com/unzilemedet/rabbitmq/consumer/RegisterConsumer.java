package com.unzilemedet.rabbitmq.consumer;


import com.unzilemedet.rabbitmq.model.RegisterModel;
import com.unzilemedet.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j //konsolda loglama işlemleri için kullanılır(Simple Logging Facade for Java)
public class RegisterConsumer {
    private final UserService userProfileService;

    @RabbitListener(queues = ("${rabbitmq.queueRegister}"))
    public void newUserCreate(RegisterModel model){
        log.info("User {}", model.toString());
        userProfileService.createUserWithRabbitMq(model);
    }
}
