package com.example.healthmanagement.controller;

import com.example.healthmanagement.dtos.CreateVisitRequest;
import com.example.healthmanagement.dtos.VisitResponse;
import com.example.healthmanagement.service.VisitService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/visits")
@RequiredArgsConstructor
public class VisitController {

    private final VisitService visitService;

    @PreAuthorize("hasAuthority('CLINICIAN')")
    @PostMapping
    public ResponseEntity<VisitResponse> createVisit(
            @RequestBody @Valid CreateVisitRequest request) {
        return ResponseEntity.ok(visitService.createVisit(request));
    }

    @PreAuthorize("hasAuthority('CLINICIAN') or hasAuthority('ADMINISTRATOR')")
    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<VisitResponse>> getPatientVisits(
            @PathVariable Long patientId) {
        return ResponseEntity.ok(visitService.getPatientVisits(patientId));
    }

    @PreAuthorize("hasAuthority('CLINICIAN') or hasAuthority('NUTRITIONIST')")
    @GetMapping("/{visitId}")
    public ResponseEntity<VisitResponse> getVisitById(
            @PathVariable Long visitId) {
        return ResponseEntity.ok(visitService.getVisitById(visitId));
    }
}