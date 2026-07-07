package com.example.healthmanagement.controller;

import com.example.healthmanagement.dtos.*;
import com.example.healthmanagement.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/patient")
public class PatientController {

    private final PatientService patientService;

    // HATUA YA 2 — Medical History
    @PreAuthorize("hasAuthority('PATIENT')")
    @PostMapping("/medical-history")
    public ResponseEntity<String> addMedicalHistory(
            @RequestBody MedicalHistoryRequest request) {
        return ResponseEntity.ok(patientService.addMedicalReport(request));
    }

    @PreAuthorize("hasAuthority('PATIENT')")
    @GetMapping("/medical-history")
    public ResponseEntity<MedicalHistoryRequest> getMedicalHistory() {
        return ResponseEntity.ok(patientService.getMedicalReport());
    }

    // HATUA YA 3 — Appointments
    @PreAuthorize("hasAuthority('PATIENT')")
    @PostMapping("/appointment/{clinicianId}")
    public ResponseEntity<AppointmentResponse> makeAppointment(
            @RequestBody AppointmentRequest request,
            @PathVariable Long clinicianId) {
        return ResponseEntity.ok(patientService.makeAppointment(request, clinicianId));
    }

    @PreAuthorize("hasAuthority('PATIENT')")
    @GetMapping("/appointments")
    public ResponseEntity<List<AppointmentResponse>> getMyAppointments() {
        return ResponseEntity.ok(patientService.getMyAppointments());
    }


    //Anthropometrics
    @PreAuthorize("hasAuthority('PATIENT')")
    @PostMapping("/anth/{phone}")
    public ResponseEntity<AnthropometricsResponse> response(@RequestBody AnthropometricsRequest request,@PathVariable  String phone) {

        return ResponseEntity.ok(patientService.anthropometrics(request,phone));
    }

}