package com.example.healthmanagement.repository;

import com.example.healthmanagement.model.Visit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitRepository extends JpaRepository<Visit, Long> {
}
