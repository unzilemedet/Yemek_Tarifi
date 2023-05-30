package com.unzilemedet.manager;


import com.unzilemedet.dto.response.UserProfileResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "http://localhost:7090/api/v1/user", name = "recipe-user")
public interface IUserProfileManager {

    @GetMapping("/find-by-userprofile-dto/{authId}")
    public ResponseEntity<UserProfileResponseDto> findByAuthId(@PathVariable Long authId);
}
