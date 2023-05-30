package com.unzilemedet.mapper;

import com.unzilemedet.dto.request.RegisterRequestDto;
import com.unzilemedet.dto.request.NewCreateUserRequestDto;
import com.unzilemedet.dto.request.ToUserServiceRequestDto;
import com.unzilemedet.dto.request.UpdateEmailOrUsernameRequestDto;
import com.unzilemedet.dto.response.RegisterResponseDto;
import com.unzilemedet.rabbitmq.model.RegisterMailModel;
import com.unzilemedet.rabbitmq.model.RegisterModel;
import com.unzilemedet.repository.entity.Auth;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface IAuthMapper {
    IAuthMapper INSTANCE = Mappers.getMapper(IAuthMapper.class);

    Auth fromDtoToAuth(final RegisterRequestDto dto);

    RegisterResponseDto toRegisterResponseDto(final Auth auth);
    @Mapping(source = "id", target = "authId")
    ToUserServiceRequestDto fromAuthToUserService(final Auth auth);
    @Mapping(source = "id", target = "authId")
    RegisterModel fromAuthToRegisterModel(final Auth auth);
    RegisterMailModel fromAuthToRegisterMailModel(final Auth auth);
    Auth fromRequestDtoToAuth(RegisterRequestDto dto);
    RegisterResponseDto fromAuthToResponseDto(Auth auth);
    @Mapping(source = "id", target="authId")
    NewCreateUserRequestDto fromAuthToNewCreateUserDto(Auth auth);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUsernameOrEmail(UpdateEmailOrUsernameRequestDto dto, @MappingTarget Auth auth);

}

