package com.example.healthmanagement.repository;

import com.example.healthmanagement.model.NutritionDiagnosis;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NutritionDiagnosisRepository extends JpaRepository<NutritionDiagnosis, Long> {
}
