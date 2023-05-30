package com.unzilemedet.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ToUserServiceRequestDto {
    private String authId;
    private String username;//username
    private String surname;
    private String email;
    private String address;
}
