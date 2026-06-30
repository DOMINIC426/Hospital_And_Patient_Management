package com.example.healthmanagement.repository;

import com.example.healthmanagement.model.NutritionAssessment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NutritionAssessmentRepository extends JpaRepository<NutritionAssessment, Long> {
}
