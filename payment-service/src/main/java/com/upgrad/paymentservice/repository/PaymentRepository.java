package com.upgrad.paymentservice.repository;

import com.upgrad.paymentservice.dao.PaymentDao;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends MongoRepository<PaymentDao, String> {
}
