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
public class Favorite extends Base {
    @Id
    private String id;
    private String userId;
    private String recipeId;
    private String username;
    private String avatar;
}
