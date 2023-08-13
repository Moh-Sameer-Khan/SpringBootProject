package com.geekster.DoctorApp.model.dataToObject;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignInInput {

    @Pattern(regexp = "\\b[A-Za-z0-9._%+-]+@gmail\\.com\\b")
    private String email;

    @NotBlank
    private String password;
}
