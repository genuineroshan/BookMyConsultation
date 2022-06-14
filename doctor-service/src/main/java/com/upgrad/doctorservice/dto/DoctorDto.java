package com.upgrad.doctorservice.dto;


import com.upgrad.doctorservice.validator.DateFormatValidator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DoctorDto {
    private String id;
    @Size(max = 20)
    @NotBlank
    private String firstName, lastName;
    @DateFormatValidator
    private String dob;
    @Email
    @NotBlank
    private String emailId;
    @Pattern(regexp = "(^$|[0-9]{10})")
    private String mobile;
    @Pattern(regexp = "[A-Z]{5}[0-9]{4}[A-Z]{1}")
    @NotBlank
    private String pan;
    private String speciality;
    private String registrationDate;
    private String status;
    private String verifiedBy;
    private String verifierComments;
    private String verificationDate;
    private double ratings;
    private int noOfRatings;
}
