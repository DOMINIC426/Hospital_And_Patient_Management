package com.example.healthmanagement.repository;

import com.example.healthmanagement.model.LaboratoryResults;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LaboratoryResultsRepository extends JpaRepository<LaboratoryResults, Long> {
}
