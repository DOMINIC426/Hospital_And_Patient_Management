package com.example.healthmanagement.model;

import com.example.healthmanagement.model.enums.TriageLevel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "visits")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long visitId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private LocalDate visitDate;
    private String chiefComplaint;
    private String duration;
    private String severity;
    private String associatedSymptoms;
    private String onset;

    @Enumerated(EnumType.STRING)
    private TriageLevel triageLevel;

    @Column(columnDefinition = "TEXT")
    private String clinicianNotes;

    @OneToOne(mappedBy = "visit", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private NutritionAssessment nutritionAssessment;

    @OneToOne(mappedBy = "visit", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Anthropometrics anthropometrics;

    @OneToOne(mappedBy = "visit", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private MentalHealthScreen mentalHealthScreen;

    @OneToOne(mappedBy = "visit", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private LaboratoryResults laboratoryResults;

    @OneToOne(mappedBy = "visit", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private NutritionDiagnosis nutritionDiagnosis;
}
