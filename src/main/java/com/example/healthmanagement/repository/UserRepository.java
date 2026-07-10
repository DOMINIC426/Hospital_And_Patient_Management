package com.example.healthmanagement.repository;

import com.example.healthmanagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface UserRepository extends JpaRepository<User, Long> {


    User findByContactPhone(String phone);

    @Query("SELECT u FROM User u WHERE u.userId = :user_id")
    User findClinicianById(@Param("user_id") Long user_id);

    //find the user with the role of clinician only
    @Query("""
      SELECT u FROM User u WHERE u.role ='CLINICIAN'
""")
    List<User> findAllClinician();

}
