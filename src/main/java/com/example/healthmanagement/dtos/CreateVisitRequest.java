package com.example.healthmanagement.dtos;

import com.example.healthmanagement.model.enums.TriageLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateVisitRequest {

    private LocalDate visitDate;
    private Long patientId; // which patient this visit belongs to

    private String chiefComplaint;
    private String duration;
    private String severity;
    private String associatedSymptoms;
    private String onset;

    private TriageLevel triageLevel;
    private String clinicianNotes;

    // Anthropometrics (optional)
    private Double weight;
    private Double height;
    private Double muac;
    private Double waistCircumference;
    private Double hipCircumference;
    private Double bmi;
    private Double bmiForAgeZScore;
    private Double whr;
    private String bloodPressure;
    private Double hemoglobin;
    private Double bloodGlucose;

    // Mental health screen (optional)
    private String stressLevel;
    private String sleepQuality;
    private Integer phq2Score;
    private Integer gad2Score;
    private Boolean referralFlag;

    // Laboratory results (optional)
    private String cbc;
    private Double hba1c;
    private String lipidProfile;
    private Double creatinine;
    private String liverFunction;
    private String electrolytes;
    private String urinalysis;

    // Nutrition diagnosis (optional)
    private String problem;
    private String etiology;
    private String signsSymptoms;
    private String intervention;
    private String prescription;
    private String monitoringIndicators;

    // Nutrition assessment (optional)
    private String dailyMeals;
    private String dietaryRestrictions;
    private String waterIntake;
    private String physicalActivityLevel;
    private Double sleepHours;
    private String alcoholUse;
    private String tobaccoUse;
}

