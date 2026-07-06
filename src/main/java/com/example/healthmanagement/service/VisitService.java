package com.example.healthmanagement.service;

import com.example.healthmanagement.dtos.CreateVisitRequest;
import com.example.healthmanagement.dtos.VisitResponse;
import com.example.healthmanagement.model.*;
import com.example.healthmanagement.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VisitService {

    private final VisitRepository visitRepository;
    private final UserRepository userRepository;
    private final AnthropometricsRepository anthropometricsRepository;
    private final MentalHealthScreenRepository mentalHealthScreenRepository;
    private final LaboratoryResultsRepository laboratoryResultsRepository;
    private final NutritionDiagnosisRepository nutritionDiagnosisRepository;
    private final NutritionAssessmentRepository nutritionAssessmentRepository;

    @Transactional
    public VisitResponse createVisit(CreateVisitRequest request) {

        // get logged-in clinician from security context
        String clinicianPhone = SecurityContextHolder.getContext()
                .getAuthentication().getName();
        User clinician = userRepository.findByContactPhone(clinicianPhone);

        // get patient by id
        User patient = userRepository.findById(request.getPatientId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        // 1. Save the Visit first
        Visit visit = new Visit();
        visit.setUser(patient);
        visit.setVisitDate(request.getVisitDate());
        visit.setChiefComplaint(request.getChiefComplaint());
        visit.setDuration(request.getDuration());
        visit.setSeverity(request.getSeverity());
        visit.setAssociatedSymptoms(request.getAssociatedSymptoms());
        visit.setOnset(request.getOnset());
        visit.setTriageLevel(request.getTriageLevel());
        visit.setClinicianNotes(request.getClinicianNotes());
        Visit savedVisit = visitRepository.save(visit);

        // 2. Save Anthropometrics if weight or height provided
        if (request.getWeight() != null || request.getHeight() != null) {
            Anthropometrics anthropometrics = new Anthropometrics();
            anthropometrics.setVisit(savedVisit);
            anthropometrics.setWeight(request.getWeight());
            anthropometrics.setHeight(request.getHeight());
            anthropometrics.setMuac(request.getMuac());
            anthropometrics.setWaistCircumference(request.getWaistCircumference());
            anthropometrics.setHipCircumference(request.getHipCircumference());
            anthropometrics.setBloodPressure(request.getBloodPressure());
            anthropometrics.setHemoglobin(request.getHemoglobin());
            anthropometrics.setBloodGlucose(request.getBloodGlucose());

            // auto-calculate BMI if weight and height are both provided
            if (request.getWeight() != null && request.getHeight() != null
                    && request.getHeight() > 0) {
                double heightInMeters = request.getHeight() / 100.0;
                double bmi = request.getWeight() / (heightInMeters * heightInMeters);
                anthropometrics.setBmi(Math.round(bmi * 100.0) / 100.0);
            }

            // auto-calculate WHR if waist and hip are both provided
            if (request.getWaistCircumference() != null
                    && request.getHipCircumference() != null
                    && request.getHipCircumference() > 0) {
                double whr = request.getWaistCircumference() / request.getHipCircumference();
                anthropometrics.setWhr(Math.round(whr * 100.0) / 100.0);
            }

            anthropometricsRepository.save(anthropometrics);
        }

        // 3. Save Mental Health Screen if any score provided
        if (request.getPhq2Score() != null || request.getGad2Score() != null) {
            MentalHealthScreen mentalHealth = new MentalHealthScreen();
            mentalHealth.setVisit(savedVisit);
            mentalHealth.setStressLevel(request.getStressLevel());
            mentalHealth.setSleepQuality(request.getSleepQuality());
            mentalHealth.setPhq2Score(request.getPhq2Score());
            mentalHealth.setGad2Score(request.getGad2Score());

            // auto-set referral flag if PHQ2 >= 3 OR GAD2 >= 3
            boolean referral = (request.getPhq2Score() != null && request.getPhq2Score() >= 3)
                    || (request.getGad2Score() != null && request.getGad2Score() >= 3);
            mentalHealth.setReferralFlag(referral);

            mentalHealthScreenRepository.save(mentalHealth);
        }

        // 4. Save Laboratory Results if any lab field provided
        if (request.getCbc() != null || request.getHba1c() != null
                || request.getLipidProfile() != null) {
            LaboratoryResults labs = new LaboratoryResults();
            labs.setVisit(savedVisit);
            labs.setCbc(request.getCbc());
            labs.setHba1c(request.getHba1c());
            labs.setLipidProfile(request.getLipidProfile());
            labs.setCreatinine(request.getCreatinine());
            labs.setLiverFunction(request.getLiverFunction());
            labs.setElectrolytes(request.getElectrolytes());
            labs.setUrinalysis(request.getUrinalysis());
            laboratoryResultsRepository.save(labs);
        }

        // 5. Save Nutrition Diagnosis if problem provided
        if (request.getProblem() != null) {
            NutritionDiagnosis diagnosis = new NutritionDiagnosis();
            diagnosis.setVisit(savedVisit);
            diagnosis.setProblem(request.getProblem());
            diagnosis.setEtiology(request.getEtiology());
            diagnosis.setSignsSymptoms(request.getSignsSymptoms());
            diagnosis.setIntervention(request.getIntervention());
            diagnosis.setPrescription(request.getPrescription());
            diagnosis.setMonitoringIndicators(request.getMonitoringIndicators());
            nutritionDiagnosisRepository.save(diagnosis);
        }

        // 6. Save Nutrition Assessment if dailyMeals provided
        if (request.getDailyMeals() != null) {
            NutritionAssessment nutrition = new NutritionAssessment();
            nutrition.setVisit(savedVisit);
            nutrition.setDailyMeals(request.getDailyMeals());
            nutrition.setDietaryRestrictions(request.getDietaryRestrictions());
            nutrition.setWaterIntake(request.getWaterIntake());
            nutrition.setPhysicalActivityLevel(request.getPhysicalActivityLevel());
            nutrition.setSleepHours(request.getSleepHours());
            nutrition.setAlcoholUse(request.getAlcoholUse());
            nutrition.setTobaccoUse(request.getTobaccoUse());
            nutritionAssessmentRepository.save(nutrition);
        }

        return toVisitResponse(savedVisit, patient);
    }

    // get all visits for a specific patient
    public List<VisitResponse> getPatientVisits(Long patientId) {
        User patient = userRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        return visitRepository.findByUser(patient)
                .stream()
                .map(visit -> toVisitResponse(visit, patient))
                .toList();
    }

    // get single visit by id
    public VisitResponse getVisitById(Long visitId) {
        Visit visit = visitRepository.findById(visitId)
                .orElseThrow(() -> new RuntimeException("Visit not found"));
        return toVisitResponse(visit, visit.getUser());
    }

    private VisitResponse toVisitResponse(Visit visit, User patient) {
        return new VisitResponse(
                visit.getVisitId(),
                patient.getUserId(),
                patient.getFirstName() + " " + patient.getLastName(),
                visit.getVisitDate(),
                visit.getChiefComplaint(),
                visit.getSeverity(),
                visit.getOnset(),
                visit.getDuration(),
                visit.getAssociatedSymptoms(),
                visit.getTriageLevel(),
                visit.getClinicianNotes()
        );
    }
}