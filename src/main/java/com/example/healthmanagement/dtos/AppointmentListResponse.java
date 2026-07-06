package com.example.healthmanagement.dtos;

import com.example.healthmanagement.model.enums.AppointmentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentListResponse {
    private Long appointmentId;
    private LocalDate dateTime;
    private Long patientId;
    private String patientName;
    private Integer queueNumber;
    private AppointmentStatus status;
}