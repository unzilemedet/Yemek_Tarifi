package com.unzilemedet.controller;


import com.unzilemedet.dto.request.ActivateRequestDto;
import com.unzilemedet.dto.request.RegisterRequestDto;
import com.unzilemedet.dto.request.LoginRequestDto;
import com.unzilemedet.dto.request.UpdateEmailOrUsernameRequestDto;
import com.unzilemedet.dto.response.RegisterResponseDto;
import com.unzilemedet.repository.entity.Auth;
import com.unzilemedet.repository.entity.enums.ERole;
import com.unzilemedet.service.AuthService;
import com.unzilemedet.utility.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.unzilemedet.constant.ApiUrls.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
public class AuthController {
    private final AuthService authService;
    private final JwtTokenProvider tokenProvider;
  /*  @PostMapping("/create")
    public ResponseEntity<Boolean> create(@RequestBody RegisterRequestDto dto){
        return ResponseEntity.ok(authService.createAuth(dto));
    }*/
    @PostMapping("/register2")
    public ResponseEntity<RegisterResponseDto> register(@RequestBody @Valid RegisterRequestDto dto) {
        return ResponseEntity.ok(authService.register(dto));
    }
    @PostMapping(REGISTER) //register2
    public ResponseEntity<RegisterResponseDto> registerWithRabbitMq(@RequestBody @Valid RegisterRequestDto dto){
        return ResponseEntity.ok(authService.registerWithRabbitMq(dto));
    }
    @GetMapping(FIND_ALL)
    public ResponseEntity<List<Auth>> findAll() {
        return ResponseEntity.ok(authService.findAll());
    }
    @PostMapping(ACTIVATE_STATUS)
    public ResponseEntity<Boolean> activateStatus(@RequestBody ActivateRequestDto dto){
        return  ResponseEntity.ok(authService.activateStatus(dto));
    }

    @PostMapping(LOGIN)
    public ResponseEntity<String> login(@RequestBody  LoginRequestDto dto){
        return ResponseEntity.ok(authService.login(dto));
    }
    @PutMapping("/update-username-email")
    public ResponseEntity<Boolean> update(@RequestBody UpdateEmailOrUsernameRequestDto dto){
        return ResponseEntity.ok(authService.update(dto));
    }

    @GetMapping("/create-token-with-id")
    public ResponseEntity<String> createToken(Long id){
        return ResponseEntity.ok(tokenProvider.createToken(id).get());
    }

    @GetMapping("/create-token-with-role")
    public ResponseEntity<String> createToken(Long id, ERole role){
        return ResponseEntity.ok(tokenProvider.createToken(id, role).get());
    }

    @GetMapping("/get-id-from-token")
    public ResponseEntity<Long> getIdFromToken(String token){
        return ResponseEntity.ok(tokenProvider.getIdFromToken(token).get());
    }

    @GetMapping("/get-role-from-token")
    public ResponseEntity<String> getRoleFromToken(String token){
        return ResponseEntity.ok(tokenProvider.getRoleFromToken(token).get());
    }

  @GetMapping("/find-by-role/{role}")
    public ResponseEntity<List<Long>> findByRole(@PathVariable String role){
        return ResponseEntity.ok(authService.findByRole(role));
    }

}