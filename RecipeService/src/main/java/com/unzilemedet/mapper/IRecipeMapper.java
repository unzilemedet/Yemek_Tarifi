package com.unzilemedet.mapper;

import com.unzilemedet.dto.request.CreateNewRecipeRequestDto;
import com.unzilemedet.dto.response.UserProfileResponseDto;
import com.unzilemedet.repository.entity.Favorite;
import com.unzilemedet.repository.entity.Recipe;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,componentModel = "spring")
public interface IRecipeMapper {
    IRecipeMapper INSTANCE= Mappers.getMapper(IRecipeMapper.class);

    Recipe toRecipe(final CreateNewRecipeRequestDto dto);

    @Mapping(source = "id", target = "userId")
    Favorite toFavorite(final UserProfileResponseDto userProfile);

}
