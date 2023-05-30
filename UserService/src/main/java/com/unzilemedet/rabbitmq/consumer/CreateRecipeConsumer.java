package com.unzilemedet.rabbitmq.consumer;


import com.unzilemedet.rabbitmq.model.CreateRecipeModel;
import com.unzilemedet.rabbitmq.model.UserProfileResponseModel;
import com.unzilemedet.repository.entity.User;
import com.unzilemedet.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CreateRecipeConsumer {
    private final UserService userProfileService;

    @RabbitListener(queues = ("${rabbitmq.queueCreateRecipe}"))
    public Object createRecipe(CreateRecipeModel model){
        Optional<User> userProfile = userProfileService.findByAuthId(model.getAuthId());
        UserProfileResponseModel userProfileResponseModel = UserProfileResponseModel.builder()
                .userId(userProfile.get().getId())
                .username(userProfile.get().getUsername())
                .avatar(userProfile.get().getAvatar())
                .build();
        return userProfileResponseModel;
    }
}
