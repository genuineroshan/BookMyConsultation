package com.upgrad.appointmentservice.repository;

import com.upgrad.appointmentservice.dao.PrescriptionDao;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrescriptionRepository extends MongoRepository<PrescriptionDao, String> {
}
