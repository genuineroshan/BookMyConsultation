package com.upgrad.appointmentservice.dao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="availability")
public class AvailabilityDao {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int availabilityId;
    private String timeSlot;
    private LocalDate availabilityDate;
    private String doctorId;
    private boolean isBooked = false;
}
