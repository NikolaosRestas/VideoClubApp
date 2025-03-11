package com.example.VideoClubApp.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PsGamesRequestDto {
    private String title;
    private String console;
    private String company;
    private Long videoClubId;
    private Long customerId;
}
