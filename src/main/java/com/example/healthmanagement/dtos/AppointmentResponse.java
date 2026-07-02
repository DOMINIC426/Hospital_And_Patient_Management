package com.example.healthmanagement.dtos;


import com.example.healthmanagement.model.enums.AppointmentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentResponse {
    private AppointmentStatus status;
    private String message = "Your Appointment has been successfully completed Wait For  Approval.";

}
