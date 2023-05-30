package com.unzilemedet.repository;

import com.unzilemedet.repository.entity.Favorite;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IFavoriteRepository extends MongoRepository<Favorite,String> {
    Optional<Favorite> findByUserIdAndRecipeId(String userId, String recipeId);

    void deleteByUserIdAndRecipeId(String userId, String recipeId);
    void deleteAllByRecipeId(String recipeId);
}
