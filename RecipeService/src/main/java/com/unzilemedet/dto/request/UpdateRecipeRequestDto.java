package com.unzilemedet.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateRecipeRequestDto {
    String content;
    List<String> addMediaUrls;
    List<String> removeMediaUrls;
}
