package com.example.healthmanagement.model;

import com.example.healthmanagement.model.enums.AppointmentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "appointments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long appointmentId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User patient;

    @ManyToOne
    @JoinColumn(name = "clinician_id", nullable = false)
    private User clinician;

    private LocalDateTime dateTime;
    private Integer queueNumber;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus status;
}
