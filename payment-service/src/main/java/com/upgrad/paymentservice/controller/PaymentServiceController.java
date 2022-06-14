package com.upgrad.paymentservice.controller;

import com.upgrad.paymentservice.dao.PaymentDao;
import com.upgrad.paymentservice.dto.AppointmentDto;
import com.upgrad.paymentservice.dto.PaymentDto;
import com.upgrad.paymentservice.enumeration.PaymentStatus;
import com.upgrad.paymentservice.exception.RecordNotFoundException;
import com.upgrad.paymentservice.feign.AppointmentClient;
import com.upgrad.paymentservice.service.PaymentService;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@RestController
@RequestMapping("/payments")
public class PaymentServiceController {
    PaymentService paymentService;
    ModelMapper modelMapper;
    AppointmentClient appointmentClient;

    PaymentServiceController(PaymentService paymentService, ModelMapper modelMapper, AppointmentClient appointmentClient){
        this.paymentService = paymentService;
        this.modelMapper = modelMapper;
        this.appointmentClient = appointmentClient;
    }

    @PostMapping("")
    public ResponseEntity doPayment(@RequestParam int appointmentId){
        PaymentDto paymentDto = new PaymentDto();
        paymentDto.setCreatedDate(LocalDate.now());
        paymentDto.setAppointmentId(appointmentId);

        PaymentDao paymentDao = modelMapper.map(paymentDto, PaymentDao.class);
        this.paymentService.savePayment(paymentDao);
        try {
            AppointmentDto appointmentDto = this.appointmentClient.getAppointmentDetails(appointmentId);
            appointmentDto.setStatus(PaymentStatus.CONFIRMED.name());
            this.appointmentClient.updateAppointmentStatus(appointmentDto);

            return new ResponseEntity(modelMapper.map(paymentDao, PaymentDto.class), HttpStatus.OK);
        }catch (Exception exception){
            return new ResponseEntity(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
