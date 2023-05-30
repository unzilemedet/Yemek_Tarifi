package com.unzilemedet.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;



@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder


public class Base {
    private Long createdDate;
    private Long updatedDate;
}
