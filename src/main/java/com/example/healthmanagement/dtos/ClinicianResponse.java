package com.example.healthmanagement.dtos;

import com.example.healthmanagement.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ClinicianResponse {
    private String firstName;
    private String lastName;
    private Role role;
}
