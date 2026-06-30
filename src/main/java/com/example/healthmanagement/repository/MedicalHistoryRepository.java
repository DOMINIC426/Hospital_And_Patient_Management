package com.example.healthmanagement.repository;

import com.example.healthmanagement.model.MedicalHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicalHistoryRepository extends JpaRepository<MedicalHistory, Long> {
}
