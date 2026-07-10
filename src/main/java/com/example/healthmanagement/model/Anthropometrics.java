package com.example.healthmanagement.model;

import com.example.healthmanagement.model.enums.Allegies;
import com.example.healthmanagement.model.enums.BmiCondition;
import com.example.healthmanagement.model.enums.ChronicCondition;
import com.example.healthmanagement.model.enums.Feeling;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.swing.plaf.basic.BasicIconFactory;
import java.math.BigDecimal;

@Entity
@Table(name = "anthropometrics")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Anthropometrics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long anthroId;
    private Double weight;
    private Double height;
    private String bloodGroup;
    @Enumerated(EnumType.STRING)
    private Feeling feeling;
    private String symptoms;
    @Enumerated(EnumType.STRING)
    private ChronicCondition chronicCondition;
    @Enumerated(EnumType.STRING)
    private Allegies allegies;
    private String medicationName;
    private String dose;
    private String smoking;
    private String dietPreferred;
    private String familyMedicalHistory;
    private String pastSurgery;
    @Column(nullable = false,unique = true)
    private String contact;
    private String contactName;
    private Double BMI;
    @Enumerated(EnumType.STRING)
    private BmiCondition bmiCondition;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

}
