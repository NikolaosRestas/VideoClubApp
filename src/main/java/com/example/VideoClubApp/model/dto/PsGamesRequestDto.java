package com.example.VideoClubApp.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PsGamesRequestDto {
    @NotBlank(message="title must not be empty")
    private String title;
    @NotBlank(message="console must not be empty")
    private String console;
    @NotBlank(message="company must not be empty")
    private String company;
    private Long videoClubId;
    private Long customerId;
}
