package com.upgrad.doctorservice.repository;

import com.upgrad.doctorservice.dao.DoctorDao;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends MongoRepository<DoctorDao, String> {
    List<DoctorDao> findAllByStatusAndSpeciality(String status, String speciality);
}
