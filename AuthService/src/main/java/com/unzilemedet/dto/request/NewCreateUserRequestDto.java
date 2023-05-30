package com.unzilemedet.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewCreateUserRequestDto {
    private Long authId;
    private String username; //name
    private String email;

}
