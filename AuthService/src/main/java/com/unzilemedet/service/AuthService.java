package com.unzilemedet.service;

import com.unzilemedet.dto.request.*;
import com.unzilemedet.dto.response.RegisterResponseDto;
import com.unzilemedet.exception.AuthManagerException;
import com.unzilemedet.exception.ErrorType;
import com.unzilemedet.manager.IUserManager;
import com.unzilemedet.mapper.IAuthMapper;
import com.unzilemedet.rabbitmq.producer.RegisterMailProducer;
import com.unzilemedet.rabbitmq.producer.RegisterProducer;
import com.unzilemedet.repository.IAuthRepository;

import com.unzilemedet.repository.entity.Auth;
import com.unzilemedet.repository.entity.enums.ERole;
import com.unzilemedet.repository.entity.enums.EStatus;
import com.unzilemedet.utility.CodeGenerator;
import com.unzilemedet.utility.JwtTokenProvider;
import com.unzilemedet.utility.ServiceManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.unzilemedet.utility.CodeGenerator.generateCode;

@Service

public class AuthService extends ServiceManager<Auth,Long> {

    private final IAuthRepository authRepository;
    private final IUserManager userManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final RegisterProducer registerProducer;
    private final RegisterMailProducer registerMailProducer;


    public AuthService(JpaRepository<Auth, Long> repository,IUserManager userManager,IAuthRepository authRepository, JwtTokenProvider jwtTokenProvider, RegisterProducer registerProducer,RegisterMailProducer registerMailProducer) {
        super(repository);
        this.userManager= userManager;
        this.authRepository=authRepository;
        this.jwtTokenProvider=jwtTokenProvider;
        this.registerProducer=registerProducer;
        this.registerMailProducer=registerMailProducer;
    }
   public RegisterResponseDto register(RegisterRequestDto dto){
        Auth auth = IAuthMapper.INSTANCE.fromRequestDtoToAuth(dto);
        if(dto.getPassword().equals(dto.getRepassword())) {
            auth.setActivationCode(generateCode());
            save(auth);
            userManager.createUser(IAuthMapper.INSTANCE.fromAuthToNewCreateUserDto(auth));
        }else{
            throw new AuthManagerException(ErrorType.PASSWORD_ERROR);
        }
        RegisterResponseDto responseDto = IAuthMapper.INSTANCE.fromAuthToResponseDto(auth);
        return responseDto;

    }
    public RegisterResponseDto registerWithRabbitMq(RegisterRequestDto dto){
        Auth auth = IAuthMapper.INSTANCE.fromRequestDtoToAuth(dto);
        if (dto.getPassword().equals(dto.getRepassword())){
            auth.setActivationCode(generateCode());
           // auth.setPassword(passwordEncoder.encode(dto.getPassword()));
            save(auth);
            registerProducer.sendNewUser(IAuthMapper.INSTANCE.fromAuthToRegisterModel(auth));
            registerMailProducer.sendActivationCode(IAuthMapper.INSTANCE.fromAuthToRegisterMailModel(auth));
        }else {
            throw new AuthManagerException(ErrorType.PASSWORD_ERROR);
        }
        RegisterResponseDto responseDto = IAuthMapper.INSTANCE.fromAuthToResponseDto(auth);
        return responseDto;
    }

   /* public Optional<Auth> doLogin(LoginRequestDto dto){
        return authRepository.findOptionalByEmailAndPassword(
                dto.getEmail(),dto.getPassword()
        );
    }*/

   public Boolean activateStatus(ActivateRequestDto dto){
        Optional<Auth> auth = findById(dto.getId());
        if(auth.isEmpty()){
            throw new AuthManagerException(ErrorType.USER_NOT_FOUND);
        }else if (auth.get().getActivationCode().equals(dto.getActivationCode())){
            auth.get().setStatus(EStatus.ACTIVE);
            update(auth.get());
            userManager.activateStatus(auth.get().getId());
            return true;
        }
        throw new AuthManagerException(ErrorType.ACTIVATE_CODE_ERROR);
    }

    public String login(LoginRequestDto dto) {
        Optional<Auth> auth = authRepository.findOptionalByEmailAndPassword(dto.getEmail(),dto.getPassword());
        if (auth.isEmpty()) {
            throw new AuthManagerException(ErrorType.LOGIN_ERROR);
        } else if (!auth.get().getStatus().equals(EStatus.ACTIVE)) {
            throw new AuthManagerException(ErrorType.ACTIVATE_CODE_ERROR) ;
        }
        return jwtTokenProvider.createToken(auth.get().getId() , auth.get().getRole()).orElseThrow(() -> {throw new AuthManagerException(ErrorType.TOKEN_NOT_CREATED);
        });
    }

 /*   public  String login(LoginRequestDto dto){
        Optional<Auth> auth =authRepository.findOptionalByEmailAndPassword(dto.getEmail(),dto.getPassword());
        if(auth.isEmpty()){
            throw  new AuthManagerException(ErrorType.LOGIN_ERROR);
        } else if (!auth.get().getStatus().equals(EStatus.ACTIVE)) {
            throw  new AuthManagerException(ErrorType.USER_NOT_FOUND);

        }return jwtTokenProvider.createToken(auth.get().getId() , auth.get().getRole()).orElseThrow(() -> {throw new AuthManagerException(ErrorType.TOKEN_NOT_CREATED);

        });
    }*/
    public Boolean update(UpdateEmailOrUsernameRequestDto dto){
        Optional<Auth> auth = authRepository.findById(dto.getAuthId());
        if (auth.isEmpty()){
            throw new AuthManagerException(ErrorType.USER_NOT_FOUND);
        }
        /*
        user-service'de UserService içerisindeki UserProfile parametresi ile çözüm için kullanılacak.
        auth.get().setUsername(dto.getUsername());
        auth.get().setEmail(dto.getEmail());
        */
        IAuthMapper.INSTANCE.updateUsernameOrEmail(dto, auth.get());
        update(auth.get());
        return true;
    }
    public List<Long> findByRole(String role) {
        ERole roles = ERole.valueOf(role.toUpperCase(Locale.ENGLISH));
        return authRepository.findByRole(roles).stream().map(x -> x.getId()).collect(Collectors.toList());
    }

}