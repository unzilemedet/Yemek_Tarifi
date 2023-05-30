package com.unzilemedet.mapper;

import com.unzilemedet.dto.request.NewCreateUserRequestDto;
import com.unzilemedet.dto.request.ToAuthPasswordChangeDto;
import com.unzilemedet.dto.request.UpdateEmailOrUsernameRequestDto;
import com.unzilemedet.dto.request.UserProfileUpdateRequestDto;
import com.unzilemedet.dto.response.FromAuthServiceResponseDto;
import com.unzilemedet.dto.response.UserProfileResponseDto;
import com.unzilemedet.rabbitmq.model.RegisterModel;
import com.unzilemedet.repository.entity.User;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface IUserMapper {
   IUserMapper INSTANCE = Mappers.getMapper(IUserMapper.class);

   User fromDtoToUserProfile(final NewCreateUserRequestDto dto);
   User fromRegisterModelToUserProfile(final RegisterModel registerModel);

   @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
   User updateFromDtoToUserProfile(UserProfileUpdateRequestDto dto, @MappingTarget User userProfile);

   UpdateEmailOrUsernameRequestDto toUpdateUsernameEmail(final User userProfile);
   UpdateEmailOrUsernameRequestDto toUpdateUsernameEmail(final UserProfileUpdateRequestDto dto);
  // Follow fromCreateFollowDtoToFollow(final String followId, final String userId);

   ToAuthPasswordChangeDto fromUserProfileToAuthPasswordChangeDto(final User userProfile);

   @Mapping(source = "id", target = "userId")
   UserProfileResponseDto fromUserProfileToResponseDto(final User userProfile);

}
