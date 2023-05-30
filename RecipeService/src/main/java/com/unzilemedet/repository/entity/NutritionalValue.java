package com.unzilemedet.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class NutritionalValue {//besindeğeri
    @Id
    private String id;
    private String name;
    private double calorie;
    private double protein;
    private double carbohydrate;
    private double fat; // yağ
}
