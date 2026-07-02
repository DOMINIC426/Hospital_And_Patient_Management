package com.example.healthmanagement.dtos;

import com.example.healthmanagement.model.enums.Role;
import com.example.healthmanagement.model.enums.Sex;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RegisterRequest {

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotBlank(message = "Phone number is required")
    @Size(min = 10, max = 15, message = "Phone number must be between 10 and 15 characters")
    private String contactPhone;

    @NotNull(message = "Sex is required")
    private Sex sex;

    @NotNull(message = "Date of birth is required")
    private LocalDate dob;


    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;
}
