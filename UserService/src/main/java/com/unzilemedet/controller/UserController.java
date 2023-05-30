package com.unzilemedet.controller;

import com.unzilemedet.dto.request.NewCreateUserRequestDto;
import com.unzilemedet.dto.request.UserProfileUpdateRequestDto;
import com.unzilemedet.dto.response.FromAuthServiceResponseDto;
import com.unzilemedet.dto.response.UserProfileResponseDto;
import com.unzilemedet.repository.entity.User;
import com.unzilemedet.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

import static com.unzilemedet.constant.ApiUrls.*;


@RestController
@RequestMapping(USER)
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

 /*   @PostMapping("/create")
    public ResponseEntity<Boolean> createAccount(@RequestBody FromAuthServiceResponseDto dto) {
        System.out.println(dto);
        return ResponseEntity.ok(userService.createUser(dto));
    }*/

    @PostMapping("/create")
    public ResponseEntity<Boolean> createUser(@RequestBody NewCreateUserRequestDto dto){
        return ResponseEntity.ok(userService.createUser(dto));
    }

    @GetMapping(FIND_ALL)
    public ResponseEntity <List<User>> findAll(){return ResponseEntity.ok(userService.findAll());
    }
    @GetMapping(ACTIVATE_STATUS + "/{authId}")
    public ResponseEntity<Boolean> activateStatus(@PathVariable Long authId){
        return ResponseEntity.ok(userService.activateStatus(authId));
    }

    @PutMapping(UPDATE)
    public ResponseEntity<User> update(@RequestBody UserProfileUpdateRequestDto dto){
        return ResponseEntity.ok(userService.update(dto));
    }
    @DeleteMapping(DELETE_BY_ID + "/{authId}")
    public ResponseEntity<Boolean> delete(@PathVariable Long authId){
        return ResponseEntity.ok(userService.delete(authId));
    }
    @GetMapping("/find-by-username/{username}")
    public ResponseEntity<User> findByUsername(@PathVariable String username){
        return ResponseEntity.ok(userService.findByUsername(username));
    }

    @GetMapping("/find-by-role/{role}")
    public ResponseEntity<List<User>> findByRole(@PathVariable String role){
        return ResponseEntity.ok(userService.findByRole(role));
    }

    @GetMapping("/find-by-auth-id/{authId}")
    public ResponseEntity<Optional<User>> findByAuthId(@PathVariable Long authId){
        return ResponseEntity.ok(userService.findByAuthId(authId));
    }

    @GetMapping("/find-by-userprofile-dto/{authId}")
    public ResponseEntity<UserProfileResponseDto> findByUserProfileDto(@PathVariable Long authId){
        return ResponseEntity.ok(userService.findByUserProfileDto(authId));
    }
}

