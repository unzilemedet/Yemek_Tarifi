package com.unzilemedet.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FromAuthServiceResponseDto {
    private String authId;
    private String username; //name
    private String surname;
    private String email;
    private String address;
}
