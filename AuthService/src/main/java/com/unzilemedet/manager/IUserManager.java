package com.unzilemedet.manager;

import com.unzilemedet.dto.request.NewCreateUserRequestDto;
import com.unzilemedet.dto.request.ToUserServiceRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.unzilemedet.constant.ApiUrls.DELETE_BY_ID;
import static com.unzilemedet.constant.ApiUrls.FORGOT_PASSWORD;

@FeignClient(url = "http://localhost:7090/api/v1/user", name = "auth-user")
public interface IUserManager {

    @PostMapping("/create")
    public ResponseEntity<Boolean> createUser(@RequestBody NewCreateUserRequestDto dto);

    @GetMapping("/activate-status/{authId}")
    public ResponseEntity<Boolean> activateStatus(@PathVariable Long authId);
    @DeleteMapping(DELETE_BY_ID + "/{authId}")
    public ResponseEntity<Boolean> delete(@PathVariable Long authId);


}