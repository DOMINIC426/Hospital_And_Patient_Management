package com.example.healthmanagement.repository;

import com.example.healthmanagement.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
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

    // get appointments for a specific clinician only
    List<Appointment> findByClinicianUserId(Long clinicianId);

    // get appointments for a specific patient
    List<Appointment> findByPatientUserId(Long patientId);
}