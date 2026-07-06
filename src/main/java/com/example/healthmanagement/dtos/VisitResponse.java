package com.example.healthmanagement.dtos;

import com.example.healthmanagement.model.enums.TriageLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VisitResponse {
    private Long visitId;
    private Long patientId;
    private String patientName;
    private LocalDate visitDate;
    private String chiefComplaint;
    private String severity;
    private String onset;
    private String duration;
    private String associatedSymptoms;
    private TriageLevel triageLevel;
    private String clinicianNotes;
}