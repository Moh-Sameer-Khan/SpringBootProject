package com.geekster.DoctorApp.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.geekster.DoctorApp.model.enums.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, scope = Patient.class, property = "patientId")
public class Patient  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long patientId;

    private String patientName;

    @Pattern(regexp = "\\b[A-Za-z0-9._%+-]+@gmail\\.com\\b")
    @Column(unique = true)
    private String patientEmail;

    @NotBlank
    private String patientPassword;

    @Pattern(regexp = "^[0-9]{10}$")
    private String patientContact;

    private Integer patientAge;

    private String patientAddress;

    @Enumerated(EnumType.STRING)
    private Gender patientGender;

    //    Bidirectional Mapping using
    @OneToOne(mappedBy = "patient")
    private Appointment appointment;
}
