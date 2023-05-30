package com.unzilemedet.mapper;


import com.unzilemedet.dto.request.CreateNewPostRequestDto;
import com.unzilemedet.dto.response.UserProfileResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import com.unzilemedet.repository.entity.Point;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,componentModel = "spring")
public interface ICommentMapper {
    ICommentMapper INSTANCE= Mappers.getMapper(ICommentMapper.class);

    Point toPost(final CreateNewPostRequestDto dto);

    Point toPoint(final UserProfileResponseDto userProfile);

}
