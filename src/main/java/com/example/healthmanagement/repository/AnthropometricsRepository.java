package com.example.healthmanagement.repository;

import com.example.healthmanagement.model.Anthropometrics;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnthropometricsRepository extends JpaRepository<Anthropometrics, Long> {
}
