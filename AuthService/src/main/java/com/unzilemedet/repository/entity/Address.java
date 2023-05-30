package com.unzilemedet.repository.entity;

import lombok.*;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private String id;
    private String street;
    private String district;//ilçe
    private String neighborhood;//mahalle
    private String country;
    private Integer buildingNumber;
    private Integer apartmentNumber;
    private Integer postCode;
}
