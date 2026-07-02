package com.example.healthmanagement.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MedicalHistoryRequest {
 private String chronicConditions;
 private String currentMedications;
 private String allergies;
 private String pastSurgeries;
 private String familyHistory;


}
