package com.example.VideoClubApp.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieRequestDto {
    private String title;
    private String year;
    private Long videoClubId;
    private Long customerId;
}
