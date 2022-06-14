package com.upgrad.notificationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrescriptionDto {
    private String id;
    private int appointmentId;
    private String doctorId;
    private String userId;
    private String diagnosis;
    private List<Medicine> medicineList;
    private String doctorName;
    private String userName;
    private String userEmailId;
}

