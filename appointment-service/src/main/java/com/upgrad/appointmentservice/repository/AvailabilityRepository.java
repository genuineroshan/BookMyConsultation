package com.upgrad.appointmentservice.repository;

import com.upgrad.appointmentservice.dao.AvailabilityDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AvailabilityRepository extends JpaRepository<AvailabilityDao, Integer> {
    List<AvailabilityDao> findByDoctorId(String doctorId);

    Optional<AvailabilityDao> findByDoctorIdAndTimeSlotAndAvailabilityDate(String doctorId, String timeSlot, LocalDate availabilityDate);
}
