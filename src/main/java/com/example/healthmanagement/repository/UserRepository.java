package com.example.healthmanagement.repository;

import com.example.healthmanagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {


    User findByContactPhone(String phone);
}
