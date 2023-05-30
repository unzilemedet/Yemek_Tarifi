package com.unzilemedet.repository;

import com.unzilemedet.repository.entity.Recipe;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRecipeRepository extends MongoRepository<Recipe,String> {
}
