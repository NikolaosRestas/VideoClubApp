package com.example.VideoClubApp.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StaffRequestDto {
    @NotBlank(message="name must not be empty")
    private String name;
    @NotBlank(message="phone must not be empty")
    private String phone;
    private Long videoClubId;
}
