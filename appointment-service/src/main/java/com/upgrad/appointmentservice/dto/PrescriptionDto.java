package com.upgrad.appointmentservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrescriptionDto {
    private String id;
    @NotBlank
    private int appointmentId;
    @NotBlank
    private String doctorId;
    @NotBlank
    private String userId;
    @NotBlank
    private String diagnosis;
    @NotBlank
    private List<Medicine> medicineList;
    private String doctorName;
    private String userName;
    private String userEmailId;
}

