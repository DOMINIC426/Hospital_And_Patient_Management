package com.example.healthmanagement.dtos;

import com.example.healthmanagement.model.enums.Allegies;
import com.example.healthmanagement.model.enums.BmiCondition;
import com.example.healthmanagement.model.enums.ChronicCondition;
import com.example.healthmanagement.model.enums.Feeling;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class AnthropometricsResponse {
    private Double weight;
    private Double height;
    private String bloodGroup;
    private Feeling feeling;
    private String symptoms;
    private ChronicCondition chronicCondition;
    private Allegies allegies;
    private String medicationName;
    private String dose;
    private String smoking;
    private String dietPreferred;
    private String familyMedicalHistory;
    private String pastSurgery;
    private String contact;
    private String contactName;
    private String BMI;
    private BmiCondition bmiCondition;
}
