package com.example.healthmanagement.dtos;

import com.example.healthmanagement.model.enums.Role;
import com.example.healthmanagement.model.enums.Sex;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long userId;
    private String firstName;
    private String lastName;
    private LocalDate dob;
    private Sex sex;
    private String contactPhone;
    private String insuranceStatus;
    private Role role;
}