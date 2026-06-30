package com.example.healthmanagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "drug_nutrition_interactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DrugNutritionInteraction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long interactionId;

    private String medicationName;

    @Column(columnDefinition = "TEXT")
    private String interactionDescription;

    @Column(columnDefinition = "TEXT")
    private String recommendation;
}
