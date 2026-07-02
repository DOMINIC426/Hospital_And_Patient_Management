package com.example.healthmanagement.controller;

import com.example.healthmanagement.dtos.AppointmentRequest;
import com.example.healthmanagement.dtos.AppointmentResponse;
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
    public ResponseEntity<MedicalHistoryRequest> getMedicalHistory(@PathVariable Long id){
        return ResponseEntity.ok(patientService.getMedicalReport(id));
    }

    @PreAuthorize("hasAuthority('PATIENT')")
    @PostMapping("/make-appointment/{phone}/{clinicianID}")
    public ResponseEntity<AppointmentResponse> makeAppointment(@RequestBody AppointmentRequest request,@PathVariable String phone,@PathVariable Long clinicianID){
        return ResponseEntity.ok(patientService.makeAppointment(phone,request,clinicianID));
    }

}
