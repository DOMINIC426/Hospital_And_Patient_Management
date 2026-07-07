package com.example.healthmanagement.service;

import com.example.healthmanagement.dtos.*;
import com.example.healthmanagement.exception.DayIsNotValidException;
import com.example.healthmanagement.exception.UserNotFoudException;
import com.example.healthmanagement.model.Anthropometrics;
import com.example.healthmanagement.model.Appointment;
import com.example.healthmanagement.model.MedicalHistory;
import com.example.healthmanagement.model.User;
import com.example.healthmanagement.model.enums.AppointmentStatus;
import com.example.healthmanagement.model.enums.BmiCondition;
import com.example.healthmanagement.model.enums.Role;
import com.example.healthmanagement.repository.AnthropometricsRepository;
import com.example.healthmanagement.repository.AppointmentRepository;
import com.example.healthmanagement.repository.MedicalHistoryRepository;
import com.example.healthmanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final UserRepository userRepository;
    private final MedicalHistoryRepository medicalHistoryRepository;
    private final AppointmentRepository appointmentRepository;
    private final ModelMapper modelMapper;
    private final AnthropometricsRepository anthropometricsRepository;

    // HATUA YA 2 — Add Medical History
    public String addMedicalReport(MedicalHistoryRequest request) {
        // get logged-in patient from security context — never trust phone from URL
        String phone = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        User patient = userRepository.findByContactPhone(phone);
        if (patient == null) {
            throw new UserNotFoudException("User not found");
        }

        // check if medical history already exists — patient can only have one
        MedicalHistory existing = medicalHistoryRepository.findByUserContactPhone(phone);
        if (existing != null) {
            // update existing instead of creating a duplicate
            existing.setChronicConditions(request.getChronicConditions());
            existing.setCurrentMedications(request.getCurrentMedications());
            existing.setAllergies(request.getAllergies());
            existing.setPastSurgeries(request.getPastSurgeries());
            existing.setFamilyHistory(request.getFamilyHistory());
            medicalHistoryRepository.save(existing);
            return "Medical history updated successfully";
        }

        MedicalHistory medicalHistory = new MedicalHistory();
        medicalHistory.setUser(patient);
        medicalHistory.setChronicConditions(request.getChronicConditions());
        medicalHistory.setCurrentMedications(request.getCurrentMedications());
        medicalHistory.setAllergies(request.getAllergies());
        medicalHistory.setPastSurgeries(request.getPastSurgeries());
        medicalHistory.setFamilyHistory(request.getFamilyHistory());
        medicalHistoryRepository.save(medicalHistory);

        return "Medical history added successfully";
    }

    // HATUA YA 2 — Get own Medical History
    public MedicalHistoryRequest getMedicalReport() {
        // get logged-in patient from security context
        String phone = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        User patient = userRepository.findByContactPhone(phone);
        if (patient == null) {
            throw new UserNotFoudException("User not found");
        }

        MedicalHistory medicalHistory = medicalHistoryRepository
                .findByUserContactPhone(phone);

        if (medicalHistory == null) {
            throw new UserNotFoudException("Medical history not found");
        }

        // manual mapping — no ModelMapper to avoid lazy load issues
        MedicalHistoryRequest response = new MedicalHistoryRequest();
        response.setChronicConditions(medicalHistory.getChronicConditions());
        response.setCurrentMedications(medicalHistory.getCurrentMedications());
        response.setAllergies(medicalHistory.getAllergies());
        response.setPastSurgeries(medicalHistory.getPastSurgeries());
        response.setFamilyHistory(medicalHistory.getFamilyHistory());
        return response;
    }

    // HATUA YA 3 — Make Appointment
    public AppointmentResponse makeAppointment(AppointmentRequest request, Long clinicianId) {
        if (request == null || request.getDateTime() == null) {
            throw new DayIsNotValidException("Date is required");
        }

        // get logged-in patient from security context
        String phone = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        User patient = userRepository.findByContactPhone(phone);
        if (patient == null || patient.getRole() != Role.PATIENT) {
            throw new UserNotFoudException("Patient not found");
        }

        // find clinician
        User clinician = userRepository.findClinicianById(clinicianId);
        if (clinician == null || clinician.getRole() != Role.CLINICIAN) {
            throw new UserNotFoudException("Clinician not found");
        }

        // validate date is today or future
        if (request.getDateTime().isBefore(LocalDate.now())) {
            throw new DayIsNotValidException("Date must be today or in the future");
        }

        // auto-assign queue number
        Integer maxQueue = appointmentRepository
                .findMaxQueueNumberByClinicianIdAndDateTime(
                        clinician.getUserId(),
                        request.getDateTime()
                );

        Appointment appointment = new Appointment();
        appointment.setPatient(patient);
        appointment.setClinician(clinician);
        appointment.setDateTime(request.getDateTime());
        appointment.setStatus(AppointmentStatus.BOOKED);
        appointment.setQueueNumber((maxQueue == null ? 0 : maxQueue) + 1);

        Appointment saved = appointmentRepository.save(appointment);

        AppointmentResponse response = new AppointmentResponse();
        response.setAppointmentId(saved.getAppointmentId());
        response.setDateTime(saved.getDateTime());
        response.setQueueNumber(saved.getQueueNumber());
        response.setStatus(saved.getStatus());
        response.setMessage("Appointment booked successfully. Your queue number is "
                + saved.getQueueNumber());
        return response;
    }

    // get own appointments
    public java.util.List<AppointmentResponse> getMyAppointments() {
        String phone = SecurityContextHolder.getContext()
                .getAuthentication().getName();
        User patient = userRepository.findByContactPhone(phone);
        if (patient == null) {
            throw new UserNotFoudException("Patient not found");
        }

        return appointmentRepository.findByPatientUserId(patient.getUserId())
                .stream()
                .map(a -> {
                    AppointmentResponse r = new AppointmentResponse();
                    r.setAppointmentId(a.getAppointmentId());
                    r.setDateTime(a.getDateTime());
                    r.setQueueNumber(a.getQueueNumber());
                    r.setStatus(a.getStatus());
                    r.setMessage("Appointment with clinician: "
                            + a.getClinician().getFirstName()
                            + " " + a.getClinician().getLastName());
                    return r;
                })
                .toList();
    }

    //############################################################################ add the anthropometric
    @PreAuthorize("hasAuthority('PATIENT')")
    public AnthropometricsResponse anthropometrics(AnthropometricsRequest request,String phone) {

        User user = userRepository.findByContactPhone(phone);
        if (user == null) {
            throw  new UserNotFoudException("User Not Found");
        }

        Anthropometrics anthropometrics = modelMapper.map(request, Anthropometrics.class);
        //calculating the BMI
        Double BMI  = (request.getWeight()/request.getHeight());
        if(BMI<18.5){
            anthropometrics.setBmiCondition(BmiCondition.UNDER);
            anthropometrics.setBMI(BMI);
        }
        if(BMI>=18.5 && BMI<=25){
            anthropometrics.setBmiCondition(BmiCondition.HEALTH);
            anthropometrics.setBMI(BMI);
        }
        if(BMI>=25 && BMI<=30){
            anthropometrics.setBmiCondition(BmiCondition.OVER);
            anthropometrics.setBMI(BMI);
        }
        if(BMI>=30 && BMI<=35){
            anthropometrics.setBmiCondition(BmiCondition.OBESE);
            anthropometrics.setBMI(BMI);
        }


        Anthropometrics saved =anthropometricsRepository.save(anthropometrics);
        user.setAnthropometrics(saved);

        return modelMapper.map(saved, AnthropometricsResponse.class);
    }
}