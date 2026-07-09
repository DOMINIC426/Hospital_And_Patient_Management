package com.example.healthmanagement.dtos;

import com.example.healthmanagement.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterResponse {
    private String token;
    private String firstName;
    private Role role;
}
