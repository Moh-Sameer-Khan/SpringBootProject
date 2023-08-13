package com.geekster.DoctorApp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class AuthenticationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tokenId;
    private  String tokenValue;
    private LocalDateTime tokenCreationTime;

//    mapping --> unidirectional
    @OneToOne
    @JoinColumn(name = "fk_patient_id")
    private Patient patient;

    public AuthenticationToken(Patient existingPatient) {
        this.patient = existingPatient;
        this.tokenCreationTime = LocalDateTime.now();
        this.tokenValue = UUID.randomUUID().toString();
    }
}
