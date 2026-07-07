package com.example.healthmanagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "laboratory_results")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LaboratoryResults {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long labId;


    @Column(columnDefinition = "TEXT")
    private String cbc;

    private Double hba1c;

    @Column(columnDefinition = "TEXT")
    private String lipidProfile;

    private Double creatinine;

    @Column(columnDefinition = "TEXT")
    private String liverFunction;

    @Column(columnDefinition = "TEXT")
    private String electrolytes;

    @Column(columnDefinition = "TEXT")
    private String urinalysis;
}
