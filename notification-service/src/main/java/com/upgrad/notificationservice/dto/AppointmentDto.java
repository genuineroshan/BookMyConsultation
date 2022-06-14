package com.upgrad.notificationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentDto {
    private int appointmentId;
    private String doctorId;
    private String userId;
    private LocalDate appointmentDate;
    private String timeSlot;
    private String status;
    private LocalDate createdDate;
    private String priorMedicalHistory;
    private String symptoms;
    private String userEmailId;
    private String userName;
    private String doctorName;
}
