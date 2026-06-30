package com.example.healthmanagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "anthropometrics")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Anthropometrics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long anthroId;

    @OneToOne
    @JoinColumn(name = "visit_id", nullable = false)
    private Visit visit;

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
}
