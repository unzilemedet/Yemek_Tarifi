package com.unzilemedet.repository.entity;

import com.unzilemedet.repository.entity.enums.EStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Document
public class User extends Base implements Serializable{


    @Id
    private String id;
    private Long authId;
    private String name;
    private String surname;
    private String email;
    private String password;
    private String username;
    private String city;
    private String avatar;
    private String district;//ilçe
    private String neighborhood;//mahalle
    private String country;
    private Integer buildingNumber;
    private Integer apartmentNumber;
    private Integer postCode;
    @Builder.Default //bir property' ye başlangıç değeri atandığında kullanılır, new'lendiğinde kullanılmaz
    private EStatus status = EStatus.PENDING;

    //follow, follower
  //  private List<String> follows = new ArrayList<>();  //followId
  //  private List<String> followers = new ArrayList<>(); //userId
}
