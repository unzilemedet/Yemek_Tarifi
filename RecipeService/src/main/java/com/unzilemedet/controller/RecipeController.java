package com.unzilemedet.controller;


import com.unzilemedet.dto.request.CreateNewRecipeRequestDto;
import com.unzilemedet.repository.entity.Recipe;
import com.unzilemedet.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.unzilemedet.constant.ApiUrls.*;


@RestController
@RequestMapping(RECIPE)
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;

    @PostMapping(CREATE + "/{token}")
    public ResponseEntity<Recipe> createRecipe(@PathVariable String token, @RequestBody CreateNewRecipeRequestDto dto){
        return ResponseEntity.ok(recipeService.createRecipe(token, dto));
    }
    @PostMapping(CREATE + "/-with-rabbitmq" + "/{token}")
    public ResponseEntity<Recipe> createRecipeWithRabbitMq(@PathVariable String token, @RequestBody CreateNewRecipeRequestDto dto){
        return ResponseEntity.ok(recipeService.createRecipeWithRabbitMq(token, dto));
    }

    @GetMapping(FIND_ALL)
    public ResponseEntity<List<Recipe>> findAll(){
        return ResponseEntity.ok(recipeService.findAll());
    }
  /*  @PostMapping(CREATE + "/-with-rabbitmq" + "/{token}")
    public ResponseEntity<Recipe> createPostWithRabbitMq(@PathVariable String token, @RequestBody CreateNewRecipeRequestDto dto){
        return ResponseEntity.ok(recipeService.createRecipeWithRabbitMq(token, dto));
    }

    @PutMapping(UPDATE)
    public ResponseEntity<Recipe> updateRecipe(String token,String recipeId, UpdateRecipeRequestDto dto){
        return ResponseEntity.ok(recipeService.updateRecipe(token, recipeId, dto));
    }



    @PostMapping("/like-post")
    public ResponseEntity<Boolean> favoriteRecipe(String token, String recipeId){
        return ResponseEntity.ok(recipeService.favoriteRecipe(token, recipeId));
    }

    @DeleteMapping(DELETE_BY_ID)
    public ResponseEntity<Boolean> deleteRecipe(String token, String recipeId){
        return ResponseEntity.ok(recipeService.deleteRecipe(token, recipeId));
    }

    @PostMapping("/create-comment/{token}")
    public ResponseEntity<Boolean> createComment(@PathVariable String token, @RequestBody CreateCommentDto dto){
        return ResponseEntity.ok(recipeService.createComment(token, dto));
    }*/
}
