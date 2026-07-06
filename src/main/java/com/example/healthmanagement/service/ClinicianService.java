package com.example.healthmanagement.service;

import com.example.healthmanagement.dtos.AppointmentListResponse;
import com.example.healthmanagement.dtos.CreateVisitRequest;
import com.example.healthmanagement.dtos.VisitResponse;
import com.example.healthmanagement.exception.UserNotFoudException;
import com.example.healthmanagement.model.*;
import com.example.healthmanagement.model.enums.AppointmentStatus;
import com.example.healthmanagement.model.enums.Role;
import com.example.healthmanagement.repository.AppointmentRepository;
import com.example.healthmanagement.repository.UserRepository;
import com.example.healthmanagement.repository.VisitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClinicianService {

    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final VisitRepository visitRepository;

    // get own appointment list
    public List<AppointmentListResponse> getMyAppointments() {
        String phone = SecurityContextHolder.getContext()
                .getAuthentication().getName();
        User clinician = userRepository.findByContactPhone(phone);
        if (clinician == null) {
            throw new UserNotFoudException("Clinician not found");
        }

        return appointmentRepository
                .findByClinicianUserId(clinician.getUserId())
                .stream()
                .map(a -> {
                    AppointmentListResponse r = new AppointmentListResponse();
                    r.setAppointmentId(a.getAppointmentId());
                    r.setDateTime(a.getDateTime());
                    r.setPatientId(a.getPatient().getUserId());
                    r.setPatientName(a.getPatient().getFirstName()
                            + " " + a.getPatient().getLastName());
                    r.setQueueNumber(a.getQueueNumber());
                    r.setStatus(a.getStatus());
                    return r;
                })
                .toList();
    }

    // approve appointment
    public String approveAppointment(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new UserNotFoudException("Appointment not found"));
        appointment.setStatus(AppointmentStatus.APPROVED);
        appointmentRepository.save(appointment);
        return "Appointment approved successfully";
    }

    // complete appointment
    public String completeAppointment(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new UserNotFoudException("Appointment not found"));
        appointment.setStatus(AppointmentStatus.COMPLETED);
        appointmentRepository.save(appointment);
        return "Appointment marked as completed";
    }

    // cancel appointment
    public String cancelAppointment(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new UserNotFoudException("Appointment not found"));
        appointment.setStatus(AppointmentStatus.CANCELLED);
        appointmentRepository.save(appointment);
        return "Appointment cancelled successfully";
    }

    // HATUA YA 4 — Open a new Visit for a patient
    @Transactional
    public VisitResponse createVisit(Long appointmentId, CreateVisitRequest request) {

        // get logged-in clinician from security context
        String phone = SecurityContextHolder.getContext()
                .getAuthentication().getName();
        User clinician = userRepository.findByContactPhone(phone);
        if (clinician == null || clinician.getRole() != Role.CLINICIAN) {
            throw new UserNotFoudException("Clinician not found");
        }

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new UserNotFoudException("Appointment not found"));

        // verify this appointment belongs to this clinician
        if (!appointment.getClinician().getUserId().equals(clinician.getUserId())) {
            throw new IllegalArgumentException("Appointment does not belong to this clinician");
        }

        // appointment must be APPROVED before opening a visit
        if (appointment.getStatus() != AppointmentStatus.APPROVED) {
            throw new IllegalArgumentException("Appointment must be approved before opening a visit");
        }

        User patient = appointment.getPatient();

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

        // Anthropometrics
        if (request.getWeight() != null || request.getHeight() != null) {
            Anthropometrics a = new Anthropometrics();
            a.setWeight(request.getWeight());
            a.setHeight(request.getHeight());
            a.setMuac(request.getMuac());
            a.setWaistCircumference(request.getWaistCircumference());
            a.setHipCircumference(request.getHipCircumference());
            a.setBloodPressure(request.getBloodPressure());
            a.setHemoglobin(request.getHemoglobin());
            a.setBloodGlucose(request.getBloodGlucose());

            // auto-calculate BMI
            if (request.getWeight() != null && request.getHeight() != null
                    && request.getHeight() > 0) {
                double h = request.getHeight() / 100.0;
                a.setBmi(Math.round((request.getWeight() / (h * h)) * 100.0) / 100.0);
            }

            // auto-calculate WHR
            if (request.getWaistCircumference() != null
                    && request.getHipCircumference() != null
                    && request.getHipCircumference() > 0) {
                a.setWhr(Math.round((request.getWaistCircumference()
                        / request.getHipCircumference()) * 100.0) / 100.0);
            }
            a.setVisit(visit);
            visit.setAnthropometrics(a);
        }

        // Mental Health Screen
        if (request.getPhq2Score() != null || request.getGad2Score() != null) {
            MentalHealthScreen m = new MentalHealthScreen();
            m.setStressLevel(request.getStressLevel());
            m.setSleepQuality(request.getSleepQuality());
            m.setPhq2Score(request.getPhq2Score());
            m.setGad2Score(request.getGad2Score());
            // auto-set referral flag
            boolean referral = (request.getPhq2Score() != null && request.getPhq2Score() >= 3)
                    || (request.getGad2Score() != null && request.getGad2Score() >= 3);
            m.setReferralFlag(referral);
            m.setVisit(visit);
            visit.setMentalHealthScreen(m);
        }

        // Laboratory Results
        if (request.getCbc() != null || request.getHba1c() != null) {
            LaboratoryResults l = new LaboratoryResults();
            l.setCbc(request.getCbc());
            l.setHba1c(request.getHba1c());
            l.setLipidProfile(request.getLipidProfile());
            l.setCreatinine(request.getCreatinine());
            l.setLiverFunction(request.getLiverFunction());
            l.setElectrolytes(request.getElectrolytes());
            l.setUrinalysis(request.getUrinalysis());
            l.setVisit(visit);
            visit.setLaboratoryResults(l);
        }

        // Nutrition Diagnosis
        if (request.getProblem() != null) {
            NutritionDiagnosis d = new NutritionDiagnosis();
            d.setProblem(request.getProblem());
            d.setEtiology(request.getEtiology());
            d.setSignsSymptoms(request.getSignsSymptoms());
            d.setIntervention(request.getIntervention());
            d.setPrescription(request.getPrescription());
            d.setMonitoringIndicators(request.getMonitoringIndicators());
            d.setVisit(visit);
            visit.setNutritionDiagnosis(d);
        }

        // Nutrition Assessment
        if (request.getDailyMeals() != null) {
            NutritionAssessment na = new NutritionAssessment();
            na.setDailyMeals(request.getDailyMeals());
            na.setDietaryRestrictions(request.getDietaryRestrictions());
            na.setWaterIntake(request.getWaterIntake());
            na.setPhysicalActivityLevel(request.getPhysicalActivityLevel());
            na.setSleepHours(request.getSleepHours());
            na.setAlcoholUse(request.getAlcoholUse());
            na.setTobaccoUse(request.getTobaccoUse());
            na.setVisit(visit);
            visit.setNutritionAssessment(na);
        }

        Visit saved = visitRepository.save(visit);

        // mark appointment as completed after visit is opened
        appointment.setStatus(AppointmentStatus.COMPLETED);
        appointmentRepository.save(appointment);

        return toVisitResponse(saved, patient);
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