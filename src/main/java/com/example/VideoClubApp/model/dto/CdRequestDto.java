package com.example.VideoClubApp.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CdRequestDto {
    @NotBlank(message="name must not be empty")
    private String name;
    @NotBlank(message="artist must not be empty")
    private String artist;
    private Long videoClubId;
    private Long customerId;
}
