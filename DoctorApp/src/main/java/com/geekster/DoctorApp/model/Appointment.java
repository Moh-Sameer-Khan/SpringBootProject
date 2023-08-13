package com.geekster.DoctorApp.model;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.geekster.DoctorApp.model.enums.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, scope = Appointment.class, property = "appointmentId")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long appointmentId;

    private String appointmentDescription;

    private LocalDateTime appointmentScheduleTime;

    private LocalDateTime appointmentCreationTime;

//    Bidirectional Mapping using
    @OneToOne
    @JoinColumn(name = "fk_patient_id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "fk_doctor_id")
    private Doctor doctor;
}
