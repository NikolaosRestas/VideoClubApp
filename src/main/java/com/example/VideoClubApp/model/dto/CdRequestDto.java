package com.example.VideoClubApp.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CdRequestDto {
    private String name;
    private String artist;
    private Long videoClubId;
    private Long customerId;
}
