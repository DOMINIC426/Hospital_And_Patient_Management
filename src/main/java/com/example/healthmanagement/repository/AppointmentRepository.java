package com.example.healthmanagement.repository;

import com.example.healthmanagement.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query("""
           SELECT COALESCE(MAX(a.queueNumber), 0)
           FROM Appointment a
           WHERE a.clinician.userId = :clinicianId
             AND a.dateTime = :dateTime
           """)
    Integer findMaxQueueNumberByClinicianIdAndDateTime(
            @Param("clinicianId") Long clinicianId,
            @Param("dateTime") LocalDate dateTime
    );
}
