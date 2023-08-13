package com.geekster.DoctorApp.model.dataToObject;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignUpOutput {
    private Boolean signUpStatus = true;
    private String signUpStatusMessage = null;

}
