package com.example.VideoClubApp.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieRequestDto {
    @NotBlank(message="title must not be empty")
    private String title;
    @NotBlank(message="year must not be empty")
    private String year;
    private Long videoClubId;
    private Long customerId;
}
