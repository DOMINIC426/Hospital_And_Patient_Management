package com.example.healthmanagement.repository;

import com.example.healthmanagement.model.FoodRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRecommendationRepository extends JpaRepository<FoodRecommendation, Long> {
}
