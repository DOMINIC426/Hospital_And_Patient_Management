package com.example.healthmanagement.repository;

import com.example.healthmanagement.model.Anthropometrics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AnthropometricsRepository extends JpaRepository<Anthropometrics, Long> {
    @Query("""
SELECT a FROM Anthropometrics a 
WHERE a.contact = :phone
""")
    Anthropometrics findByContactUserId(@Param("phone") String phone);
}
