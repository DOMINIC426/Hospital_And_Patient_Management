package com.example.healthmanagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "population_analytics")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PopulationAnalytics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long analyticsId;

    private String month;

    @Column(columnDefinition = "TEXT")
    private String topSymptoms;

    @Column(columnDefinition = "TEXT")
    private String nutritionStatus;

    @Column(columnDefinition = "TEXT")
    private String mentalHealthTrends;

    @Column(columnDefinition = "TEXT")
    private String diseaseOutbreaks;
}
