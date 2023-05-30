package com.unzilemedet.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Document
public class Comment extends Base{
    @Id
    private String id; //3
    private String userId; //yorum yapan kişinin id'si
    private String username; //yorum yapan kişinin adı
    private String recipeId;
    private String comment;

    private List<String> subCommentId = new ArrayList<>(); //yorumun altındaki yorum --> id
    private List<String> commentId; //userId tutulacak
    private List<String> commentDislikes; //userId tutulacak
}
