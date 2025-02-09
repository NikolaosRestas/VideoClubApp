package com.example.VideoClubApp.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StaffRequestDto {
    private String name;
    private String phone;
    private Long videoClubId;
}
