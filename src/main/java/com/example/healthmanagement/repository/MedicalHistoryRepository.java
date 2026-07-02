package com.example.healthmanagement.repository;

import com.example.healthmanagement.model.MedicalHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MedicalHistoryRepository extends JpaRepository<MedicalHistory, Long> {

    @Query("SELECT m FROM MedicalHistory m WHERE m.user.id = :userId")
    MedicalHistory findByUserIdReport(@Param("userId") Long userId);
}