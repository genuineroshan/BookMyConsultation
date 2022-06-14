package com.upgrad.appointmentservice.service;

import com.upgrad.appointmentservice.dao.PrescriptionDao;
import com.upgrad.appointmentservice.repository.PrescriptionRepository;
import org.springframework.stereotype.Service;

@Service
public class PrescriptionService {
    PrescriptionRepository prescriptionRepository;

    PrescriptionService(PrescriptionRepository prescriptionRepository){
        this.prescriptionRepository = prescriptionRepository;
    }

    public void savePrescription(PrescriptionDao prescriptionDao){
        this.prescriptionRepository.save(prescriptionDao);
    }
}
