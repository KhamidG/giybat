package com.khamidgaipov.api.giybat.uz.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RegistrationDto {
    @NotBlank(message = "Name required")
    private String name;

    @NotBlank(message = "Username required")
    private String username;

    @NotBlank(message = "Password required")
    private String password;
}
