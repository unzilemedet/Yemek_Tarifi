package com.unzilemedet.service;

import com.unzilemedet.dto.request.NewCreateUserRequestDto;
import com.unzilemedet.dto.request.UpdateEmailOrUsernameRequestDto;
import com.unzilemedet.dto.request.UserProfileUpdateRequestDto;
import com.unzilemedet.dto.response.UserProfileResponseDto;
import com.unzilemedet.exception.ErrorType;
import com.unzilemedet.exception.UserManagerException;
import com.unzilemedet.manager.IAuthManager;
import com.unzilemedet.mapper.IUserMapper;
import com.unzilemedet.rabbitmq.model.RegisterModel;
import com.unzilemedet.repository.IUserRepository;
import com.unzilemedet.repository.entity.User;
import com.unzilemedet.repository.entity.enums.EStatus;
import com.unzilemedet.utility.IService;
import com.unzilemedet.utility.JwtTokenProvider;
import com.unzilemedet.utility.ServiceManager;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service

public class UserService extends ServiceManager<User,String> {
    private final IUserRepository userRepository;
    private final IAuthManager authManager;

    private final JwtTokenProvider jwtTokenProvider;
    private final CacheManager cacheManager;

    public UserService(MongoRepository<User, String> repository, IUserRepository userRepository,
                       IAuthManager authManager,JwtTokenProvider jwtTokenProvider,
                       CacheManager cacheManager){
        super(repository);
        this.userRepository=userRepository;
        this.authManager=authManager;
        this.jwtTokenProvider=jwtTokenProvider;
        this.cacheManager = cacheManager;
    }


    public Boolean createUser(NewCreateUserRequestDto dto) {
        try {
            save(IUserMapper.INSTANCE.fromDtoToUserProfile(dto));
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Beklenmeyen bir hata oluştu.");
        }
    }
    public Boolean activateStatus(Long authId) {
        Optional<User> userProfile = userRepository.findOptionalByAuthId(authId);
        if (userProfile.isEmpty()) {
            throw new RuntimeException("Auth id bulunamadı");
        }
        userProfile.get().setStatus(EStatus.ACTIVE);
        update(userProfile.get());
        return true;
    }
 //   @CacheEvict(value = "find-by-username", key = "#model.username.toLowerCase()")
    public Boolean createUserWithRabbitMq(RegisterModel model){
        try {
            User userProfile = save(IUserMapper.INSTANCE.fromRegisterModelToUserProfile(model));
            //registerElasticProducer.sendNewUser(IUserProfileMapper.INSTANCE.fromUserProfileToElasticModel(userProfile));
            cacheManager.getCache("findAll").clear();
            return true;
        }catch (Exception e){
            throw new RuntimeException("Beklenmeyen bir hata oluştu.");
        }
    }

   /* public User update(UserProfileUpdateRequestDto dto) {
        Optional<User> userProfile = userRepository.findOptionalByAuthId(dto.getAuthId());
        if (userProfile.isEmpty()) {
            throw new RuntimeException("Böyle bir kullanıcı yoktur.");
        }
        update(IUserMapper.INSTANCE.updateFromDtoToUserProfile(dto, userProfile.get()));
        authManager.updateUsernameOrEmail(IUserMapper.INSTANCE.toUpdateUsernameEmail(userProfile.get()));

        return userProfile.get();
    }*/
   public User update(UserProfileUpdateRequestDto dto){
       Optional<Long> authId = jwtTokenProvider.getIdFromToken(dto.getToken());
       if (authId.isEmpty()){
           throw new UserManagerException(ErrorType.INVALID_TOKEN);
       }
       Optional<User> userProfile = userRepository.findOptionalByAuthId(authId.get());
       if (userProfile.isEmpty()){
           throw new UserManagerException(ErrorType.USER_NOT_FOUND);
       }

       UpdateEmailOrUsernameRequestDto updateEmailOrUsernameRequestDto = IUserMapper.INSTANCE.toUpdateUsernameEmail(dto);
       updateEmailOrUsernameRequestDto.setAuthId(authId.get());
       authManager.updateUsernameOrEmail(updateEmailOrUsernameRequestDto);
       update(IUserMapper.INSTANCE.updateFromDtoToUserProfile(dto, userProfile.get()));

       return userProfile.get();
   }
    public Boolean delete(Long authId){
        Optional<User> userProfile = userRepository.findOptionalByAuthId(authId);
        if (userProfile.isEmpty()) {
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        }
        userProfile.get().setStatus(EStatus.DELETED);
        update(userProfile.get());
        return true;
    }

    @Cacheable(value = "find-by-username", key = "#username.toLowerCase()") //find-by-username(cache) --> ardaagdemir, ab123, asda, asdss
    public User findByUsername(String username){
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Optional<User> userProfile = userRepository.findOptionalByUsernameIgnoreCase(username);
        if (userProfile.isEmpty()){
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        }
        return userProfile.get();
    }
    @Cacheable(value = "find-by-role", key = "#role.toUpperCase()")  //USER, ADMIN
    public List<User> findByRole(String role){
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //auth manager
        List<Long> authIds = authManager.findByRole(role).getBody();
        return authIds.stream().map(
                        x -> userRepository.findOptionalByAuthId(x)
                                .orElseThrow(() -> {throw new UserManagerException(ErrorType.USER_NOT_FOUND);}))
                .collect(Collectors.toList());
    }

    //for FollowService
    public Optional<User> findByAuthId(Long authId){
        Optional<User> userProfile = userRepository.findOptionalByAuthId(authId);
        if (userProfile.isEmpty()){
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        }
        return userProfile; //id, username, email , password
    }

    //for post-service
    public UserProfileResponseDto findByUserProfileDto(Long authId){
        Optional<User> userProfile = userRepository.findOptionalByAuthId(authId);
        if (userProfile.isEmpty()){
            throw new UserManagerException(ErrorType.USER_NOT_FOUND);
        }
        UserProfileResponseDto userProfileResponseDto = IUserMapper.INSTANCE.fromUserProfileToResponseDto(userProfile.get());
        return userProfileResponseDto; //return userId, username, avatar

    }
}
