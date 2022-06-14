package com.upgrad.appointmentservice.service;

import com.upgrad.appointmentservice.dao.AppointmentDao;
import com.upgrad.appointmentservice.repository.AppointmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {
    AppointmentRepository appointmentRepository;
    AppointmentService(AppointmentRepository appointmentRepository){
        this.appointmentRepository = appointmentRepository;
    }

    public void saveAppointment(AppointmentDao appointmentDao)
    {
        appointmentRepository.save(appointmentDao);
    }

    public Optional<AppointmentDao> getAppointmentById(int appointmentId){
        return appointmentRepository.findById(appointmentId);
    }

    public List<AppointmentDao> getAppointmentByUserId(String userId){
        return appointmentRepository.findByUserId(userId);
    }
}
