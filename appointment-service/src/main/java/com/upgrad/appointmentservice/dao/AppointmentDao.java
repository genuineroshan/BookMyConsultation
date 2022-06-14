package com.upgrad.appointmentservice.dao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "appointment")
public class AppointmentDao {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
