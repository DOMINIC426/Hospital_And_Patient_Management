package com.example.healthmanagement.controller;

import com.example.healthmanagement.dtos.MedicalHistoryRequest;
import com.example.healthmanagement.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/patient")
public class PatientController {

    private final PatientService patientService;
    @PreAuthorize("hasAuthority('PATIENT')")
    @PostMapping("/add-medical-history/{phone}")
    public ResponseEntity<String> addMedicalHistory(@RequestBody MedicalHistoryRequest request,@PathVariable String phone){
        return ResponseEntity.ok(patientService.addMedicalReport(request,phone));
    }

    @PreAuthorize("hasAuthority('PATIENT')")
    @GetMapping("/medical-history/{id}")
    public ResponseEntity<MedicalHistoryRequest> getMedicalHistory(@RequestParam Long id){
        return ResponseEntity.ok(patientService.getMedicalReport(id));
    }

}
