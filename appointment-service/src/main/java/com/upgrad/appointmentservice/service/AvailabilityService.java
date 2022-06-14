package com.upgrad.appointmentservice.service;
import com.upgrad.appointmentservice.dao.AvailabilityDao;
import com.upgrad.appointmentservice.repository.AvailabilityRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AvailabilityService {
    AvailabilityRepository availabilityRepository;
    AvailabilityService(AvailabilityRepository availabilityRepository){
        this.availabilityRepository = availabilityRepository;
    }

    public void saveAvailability(AvailabilityDao availabilityDao){
        this.availabilityRepository.save(availabilityDao);
    }

    public List<AvailabilityDao> getAvailabilityByDoctor(String doctorId){
        return this.availabilityRepository.findByDoctorId(doctorId);
    }

    public Optional<AvailabilityDao> findByDoctorIdAndTimeSlotAndAvailabilityDate(String doctorId, String timeSlot, LocalDate availabilityDate){
        return this.availabilityRepository.findByDoctorIdAndTimeSlotAndAvailabilityDate(doctorId, timeSlot, availabilityDate);
    }
}
