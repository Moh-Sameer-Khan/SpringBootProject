package com.geekster.DoctorApp.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.geekster.DoctorApp.model.enums.Qualification;
import com.geekster.DoctorApp.model.enums.Specialization;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, scope = Doctor.class, property = "doctorId")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long doctorId;

    private String doctorName;

    @Enumerated(EnumType.STRING)
    private Specialization doctorSpecialization;

    @Pattern(regexp = "^[0-9]{10}$")
    private String doctorContactNumber;

    @Enumerated(EnumType.STRING)
    private Qualification doctorQualification;

    private Integer doctorYearsExp;

    @Min(value = 200)
    @Max(value = 2000)
    private Long doctorConsultantFees;

    //    Bidirectional Mapping using
    @OneToMany(mappedBy = "doctor")
    private List<Appointment> appointments;

}
