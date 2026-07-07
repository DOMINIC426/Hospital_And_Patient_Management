package com.example.healthmanagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "nutrition_diagnosis")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NutritionDiagnosis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long diagnosisId;


    @Column(columnDefinition = "TEXT")
    private String problem;

    @Column(columnDefinition = "TEXT")
    private String etiology;

    @Column(columnDefinition = "TEXT")
    private String signsSymptoms;

    @Column(columnDefinition = "TEXT")
    private String intervention;

    @Column(columnDefinition = "TEXT")
    private String prescription;

    @Column(columnDefinition = "TEXT")
    private String monitoringIndicators;
}
