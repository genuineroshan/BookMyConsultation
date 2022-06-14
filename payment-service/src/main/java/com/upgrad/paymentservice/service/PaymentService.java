package com.upgrad.paymentservice.service;

import com.upgrad.paymentservice.dao.PaymentDao;
import com.upgrad.paymentservice.repository.PaymentRepository;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    PaymentRepository paymentRepository;

    PaymentService(PaymentRepository paymentRepository){
        this.paymentRepository = paymentRepository;
    }

    public void savePayment(PaymentDao paymentDao){
        this.paymentRepository.save(paymentDao);
    }
}
