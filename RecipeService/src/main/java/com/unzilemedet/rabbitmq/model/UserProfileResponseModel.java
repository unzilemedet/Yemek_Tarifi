package com.unzilemedet.rabbitmq.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserProfileResponseModel implements Serializable {
    private String id;
    private String username;
    private String avatar;
}
