package com.example.healthmanagement.dtos;

import com.example.healthmanagement.model.enums.AppointmentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentResponse {
    private Long appointmentId;
    private LocalDate dateTime;
    private Integer queueNumber;
    private AppointmentStatus status;
    private String message;
}