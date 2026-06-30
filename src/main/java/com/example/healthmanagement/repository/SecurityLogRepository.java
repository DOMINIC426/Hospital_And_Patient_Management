package com.example.healthmanagement.repository;

import com.example.healthmanagement.model.SecurityLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SecurityLogRepository extends JpaRepository<SecurityLog, Long> {
}
