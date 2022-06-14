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
public class AvailabilityDto {

    private int availabilityId;

    @NotBlank
    private String timeslot;

    private LocalDate availabilityDate;

    @NotBlank
    private String doctorId;

    private boolean isBooked;
}
