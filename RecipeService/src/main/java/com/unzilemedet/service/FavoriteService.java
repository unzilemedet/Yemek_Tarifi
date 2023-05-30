package com.unzilemedet.service;

import com.unzilemedet.dto.response.UserProfileResponseDto;
import com.unzilemedet.exception.ErrorType;
import com.unzilemedet.exception.RecipeManagerException;
import com.unzilemedet.manager.IUserProfileManager;
import com.unzilemedet.repository.IFavoriteRepository;
import com.unzilemedet.repository.entity.Favorite;
import com.unzilemedet.utility.JwtTokenProvider;
import com.unzilemedet.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class FavoriteService extends ServiceManager<Favorite,String> {
    private final IFavoriteRepository favoriteRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final IUserProfileManager userProfileManager;

    public FavoriteService(IFavoriteRepository favoriteRepository, JwtTokenProvider jwtTokenProvider, IUserProfileManager userProfileManager) {
        super(favoriteRepository);
        this.favoriteRepository = favoriteRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userProfileManager = userProfileManager;
    }


    public Optional<Favorite> findByUserIdAndRecipeId(String userId, String postId){
        Optional<Favorite> favorite = favoriteRepository.findByUserIdAndRecipeId(userId, postId);
        return favorite;
    }

    public void deleteByUserIdAndRecipeId(String userId, String recipeId){
        favoriteRepository.deleteByUserIdAndRecipeId(userId, recipeId);
    }
}