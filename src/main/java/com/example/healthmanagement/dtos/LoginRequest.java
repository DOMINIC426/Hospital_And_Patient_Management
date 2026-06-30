package com.example.healthmanagement.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {

    @NotBlank(message = "Phone number is required")
    private String contactPhone;

    @NotBlank(message = "Password is required")
    private String password;
}
