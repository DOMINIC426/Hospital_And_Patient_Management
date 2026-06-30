package com.example.healthmanagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "nutrition_assessment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NutritionAssessment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long nutritionId;

    @OneToOne
    @JoinColumn(name = "visit_id", nullable = false)
    private Visit visit;

    @Column(columnDefinition = "TEXT")
    private String dailyMeals;

    @Column(columnDefinition = "TEXT")
    private String dietaryRestrictions;

    private String waterIntake;
    private String physicalActivityLevel;
    private Double sleepHours;
    private String alcoholUse;
    private String tobaccoUse;
}
