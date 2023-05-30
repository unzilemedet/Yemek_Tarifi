package com.unzilemedet.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
@Document
public class Recipe extends Base {
    @Id
    private String id;
    private String userId;
    private String name;
    private String username;
    private String avatar;
    private String content;
    private String type; //tür
    private String mediaUrls;
    private double preparationTime; //hazırlanma süresi
    private double cookingTime; //pişirme süresi
    private String recipeInformation; //tarif bilgileri
    private List<String> photos;
    private List<String> favorites = new ArrayList<>();
    private List<String> points;
    private List<String> comments;




}
