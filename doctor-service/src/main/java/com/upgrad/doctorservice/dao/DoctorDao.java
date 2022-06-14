package com.upgrad.doctorservice.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document("doctors")
public class DoctorDao {
    @Id
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
    private double rating;
    private int noOfRatings;
}
