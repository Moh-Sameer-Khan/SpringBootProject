package com.geekster.BloggingPlatformAPI.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.geekster.BloggingPlatformAPI.model.enums.AccountType;
import com.geekster.BloggingPlatformAPI.model.enums.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @NotBlank(message = "User Name can not be blank")
    private String userName;

    @NotBlank(message = "UserHandle is required")
    @Column(unique = true, nullable = false)
    private String userHandle; // Username

    private String userBio;

    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@blogsuser\\.com$")
    @Column(unique = true)
    private String userEmail;

    @NotBlank(message = "Password is Required")
    @Column(nullable = false)
    private String userPassword;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY) // hide this in json but not in database table column
    private boolean verifiedAccount;

}




