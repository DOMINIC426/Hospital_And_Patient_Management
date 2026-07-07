package com.example.healthmanagement.controller;

import com.example.healthmanagement.dtos.AppointmentListResponse;
import com.example.healthmanagement.dtos.CreateVisitRequest;
import com.example.healthmanagement.dtos.VisitResponse;
import com.example.healthmanagement.service.ClinicianService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/clinician")
public class ClinicianController {

    private final ClinicianService clinicianService;

    // get own appointments
    @PreAuthorize("hasAuthority('CLINICIAN')")
    @GetMapping("/appointments")
    public ResponseEntity<List<AppointmentListResponse>> getMyAppointments() {
        return ResponseEntity.ok(clinicianService.getMyAppointments());
    }

    // approve appointment
    @PreAuthorize("hasAuthority('CLINICIAN')")
    @PutMapping("/appointment/{appointmentId}/approve")
    public ResponseEntity<String> approveAppointment(
            @PathVariable Long appointmentId) {
        return ResponseEntity.ok(clinicianService.approveAppointment(appointmentId));
    }

    // complete appointment
    @PreAuthorize("hasAuthority('CLINICIAN')")
    @PutMapping("/appointment/{appointmentId}/complete")
    public ResponseEntity<String> completeAppointment(
            @PathVariable Long appointmentId) {
        return ResponseEntity.ok(clinicianService.completeAppointment(appointmentId));
    }

    // cancel appointment
    @PreAuthorize("hasAuthority('CLINICIAN')")
    @PutMapping("/appointment/{appointmentId}/cancel")
    public ResponseEntity<String> cancelAppointment(
            @PathVariable Long appointmentId) {
        return ResponseEntity.ok(clinicianService.cancelAppointment(appointmentId));
    }



}