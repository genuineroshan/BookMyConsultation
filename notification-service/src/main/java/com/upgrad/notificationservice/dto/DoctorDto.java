package com.upgrad.notificationservice.dto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class DoctorDto {
    private String id;
    private String firstName, lastName;
    private String dob;
    private String emailId;
    private String mobile;
    private String pan;
    private String speciality;
    private String registrationDate;
    private String status;
    private String verifiedBy;
    private String verifierComments;
    private String verificationDate;
}
