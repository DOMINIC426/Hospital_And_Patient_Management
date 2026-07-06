package com.example.healthmanagement.repository;

import com.example.healthmanagement.model.User;
import com.example.healthmanagement.model.Visit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VisitRepository extends JpaRepository<Visit, Long> {
    List<Visit> findByUser(User user);
}
