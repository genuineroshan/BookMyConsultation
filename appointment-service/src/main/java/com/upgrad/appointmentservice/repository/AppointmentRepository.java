package com.upgrad.appointmentservice.repository;

import com.upgrad.appointmentservice.dao.AppointmentDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<AppointmentDao, Integer> {
    List<AppointmentDao> findByUserId(String userId);
}
