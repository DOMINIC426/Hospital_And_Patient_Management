package com.example.healthmanagement.service;

import com.example.healthmanagement.dtos.AppointmentListResponse;
import com.example.healthmanagement.exception.UserNotFoudException;
import com.example.healthmanagement.model.*;
import com.example.healthmanagement.model.enums.AppointmentStatus;
import com.example.healthmanagement.repository.AppointmentRepository;
import com.example.healthmanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor
public class ClinicianService {

    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;


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
}