package com.example.VideoClubApp.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CustomerRequestDto {
    private String name;
    private String phone;
    private String email;
    private Long videoClubId;
}
