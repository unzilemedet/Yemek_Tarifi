package com.unzilemedet.service;

import com.unzilemedet.dto.request.CreateNewRecipeRequestDto;
import com.unzilemedet.dto.request.UpdateRecipeRequestDto;
import com.unzilemedet.dto.response.UserProfileResponseDto;
import com.unzilemedet.exception.ErrorType;
import com.unzilemedet.exception.RecipeManagerException;
import com.unzilemedet.manager.IUserProfileManager;
import com.unzilemedet.mapper.IRecipeMapper;
import com.unzilemedet.rabbitmq.model.CreateRecipeModel;
import com.unzilemedet.rabbitmq.model.UserProfileResponseModel;
import com.unzilemedet.rabbitmq.producer.CreateRecipeProducer;
import com.unzilemedet.repository.IRecipeRepository;
import com.unzilemedet.repository.entity.Favorite;
import com.unzilemedet.repository.entity.Recipe;
import com.unzilemedet.utility.JwtTokenProvider;
import com.unzilemedet.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RecipeService extends ServiceManager<Recipe,String> {
 private final IRecipeRepository recipeRepository;
 private final JwtTokenProvider jwtTokenProvider;
 private final IUserProfileManager userProfileManager;
    private final CreateRecipeProducer createRecipeProducer;
    private final FavoriteService favoriteService;

    public RecipeService(IRecipeRepository recipeRepository, JwtTokenProvider jwtTokenProvider
            ,IUserProfileManager profileManager,CreateRecipeProducer createRecipeProducer, FavoriteService favoriteService) {
        super(recipeRepository);
        this.recipeRepository = recipeRepository;
        this.jwtTokenProvider =jwtTokenProvider;
        this.userProfileManager = profileManager;
        this.createRecipeProducer = createRecipeProducer;
        this.favoriteService = favoriteService;
    }
    public Recipe createRecipe(String token, CreateNewRecipeRequestDto dto){
        Optional<Long> authId = jwtTokenProvider.getIdFromToken(token);
        if (authId.isEmpty()){
            throw new RecipeManagerException(ErrorType.INVALID_TOKEN);
        }
        UserProfileResponseDto userProfile = userProfileManager.findByAuthId(authId.get()).getBody();
        Recipe recipe = IRecipeMapper.INSTANCE.toRecipe(dto);
        recipe.setUserId(userProfile.getUserId());
        recipe.setUsername(userProfile.getUsername());
        recipe.setAvatar(userProfile.getAvatar());
        return save(recipe);
    }
    public Recipe createRecipeWithRabbitMq(String token, CreateNewRecipeRequestDto dto){
        Optional<Long> authId = jwtTokenProvider.getIdFromToken(token);
        if (authId.isEmpty()){
            throw new RecipeManagerException(ErrorType.INVALID_TOKEN);
        }
        CreateRecipeModel model = CreateRecipeModel.builder().authId(authId.get()).build();
        UserProfileResponseModel userProfile = (UserProfileResponseModel) createRecipeProducer.createRecipe(model);
        Recipe recipe = IRecipeMapper.INSTANCE.toRecipe(dto);
        recipe.setId(userProfile.getId());
        recipe.setUsername(userProfile.getUsername());
        recipe.setAvatar(userProfile.getAvatar());
        return save(recipe);
    }


}
