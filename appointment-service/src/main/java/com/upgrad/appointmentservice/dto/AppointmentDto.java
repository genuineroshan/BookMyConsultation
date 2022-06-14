package com.upgrad.appointmentservice.dto;
import com.upgrad.appointmentservice.validator.DateFormatValidator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentDto {
    private int appointmentId;
    @NotBlank
    private String doctorId;
    @NotBlank
    private String userId;
    private LocalDate appointmentDate;
    @NotBlank
    private String timeSlot;
    private String status;
    private LocalDate createdDate;
    private String priorMedicalHistory;
    private String symptoms;
    private String userEmailId;
    private String userName;
    private String doctorName;
}
