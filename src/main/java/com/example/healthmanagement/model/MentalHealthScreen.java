package com.example.healthmanagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "mental_health_screen")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MentalHealthScreen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mentalId;

    @OneToOne
    @JoinColumn(name = "visit_id", nullable = false)
    private Visit visit;

    private String stressLevel;
    private String sleepQuality;
    private Integer phq2Score;
    private Integer gad2Score;
    private Boolean referralFlag;
}
