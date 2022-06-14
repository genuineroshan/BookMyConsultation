package com.upgrad.appointmentservice.controller;

import com.upgrad.appointmentservice.dao.AppointmentDao;
import com.upgrad.appointmentservice.dao.AvailabilityDao;
import com.upgrad.appointmentservice.dao.PrescriptionDao;
import com.upgrad.appointmentservice.dto.*;
import com.upgrad.appointmentservice.enumeration.ErrorCode;
import com.upgrad.appointmentservice.enumeration.PaymentStatus;
import com.upgrad.appointmentservice.exception.RecordNotFoundException;
import com.upgrad.appointmentservice.feign.UserClient;
import com.upgrad.appointmentservice.service.AppointmentService;
import com.upgrad.appointmentservice.service.AvailabilityService;
import com.upgrad.appointmentservice.service.KafkaProducerService;
import com.upgrad.appointmentservice.service.PrescriptionService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/appointments")
public class AppointmentServiceController {

    private final PrescriptionService prescriptionService;
    private final ModelMapper modelMapper;
    private final AppointmentService appointmentService;
    private final AvailabilityService availabilityService;
    private final UserClient userClient;
    private final KafkaProducerService kafkaProducerService;

    @PostMapping("doctors/{doctorId}/availability")
    public ResponseEntity SaveAvailability(@PathVariable String doctorId, @RequestBody AvailabilityDto availabilityDto){
        availabilityDto.setDoctorId(doctorId);
        AvailabilityDao availabilityDao = modelMapper.map(availabilityDto, AvailabilityDao.class);
        Optional<AvailabilityDao> persistedAvailability = this.availabilityService.findByDoctorIdAndTimeSlotAndAvailabilityDate(
                doctorId, availabilityDto.getTimeslot(), availabilityDto.getAvailabilityDate());
        if(persistedAvailability.isEmpty()) {
            this.availabilityService.saveAvailability(availabilityDao);
            return new ResponseEntity(availabilityDao.getAvailabilityId(), HttpStatus.OK);
        }

        var errorResponse = new ErrorResponse();
        errorResponse.setErrorCode(ErrorCode.ERR_SLOT_ALREADY_AVAILABLE.name());
        errorResponse.setErrorMessage("Cannot update this availability as this slot is already available");
        return new ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/doctors/{doctorId}/availability")
    public ResponseEntity GetAvailability(@PathVariable String doctorId){
        List<AvailabilityDao> availabilityDaoList = this.availabilityService.getAvailabilityByDoctor(doctorId);
        List<AvailabilityDto> availabilityDtoList = new ArrayList<>();
        for(AvailabilityDao availabilityDao: availabilityDaoList){
            availabilityDtoList.add(modelMapper.map(availabilityDao, AvailabilityDto.class));
        }

        return new ResponseEntity(availabilityDtoList, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity BookAppointment(@RequestBody AppointmentDto appointmentDto){
        if(appointmentDto.getAppointmentId() > 0){
            this.appointmentService.saveAppointment(modelMapper.map(appointmentDto, AppointmentDao.class));
            return new ResponseEntity(HttpStatus.OK);
        }

        Optional<AvailabilityDao> persistedAvailability = this.availabilityService.findByDoctorIdAndTimeSlotAndAvailabilityDate(
                appointmentDto.getDoctorId(), appointmentDto.getTimeSlot(), appointmentDto.getAppointmentDate());
        if(persistedAvailability.isPresent()) {
            AvailabilityDao availabilityDao = persistedAvailability.get();
            if(!availabilityDao.isBooked()) {
                availabilityDao.setBooked(true);
                this.availabilityService.saveAvailability(availabilityDao);
                appointmentDto.setStatus(PaymentStatus.PENDING_PAYMENT.name());
                appointmentDto.setCreatedDate(LocalDate.now());
                DoctorDto doctorDto = this.userClient.getDoctorDetails(appointmentDto.getDoctorId());
                UserDto userDto = this.userClient.getUserDetails(appointmentDto.getUserId());
                appointmentDto.setUserEmailId(userDto.getEmailId());
                appointmentDto.setUserName(userDto.getFirstName());
                appointmentDto.setDoctorName(doctorDto.getFirstName());
                AppointmentDao appointmentDao = modelMapper.map(appointmentDto, AppointmentDao.class);
                this.appointmentService.saveAppointment(appointmentDao);
                this.kafkaProducerService.sendAppointmentMessage(appointmentDto);
                return new ResponseEntity(appointmentDao.getAppointmentId(), HttpStatus.OK);
            }
        }

        var errorResponse = new ErrorResponse();
        errorResponse.setErrorCode(ErrorCode.ERR_SLOT_NOT_AVAILABLE.name());
        errorResponse.setErrorMessage("Appointment cannot be booked as slot is not available");
        return new ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/{appointmentId}")
    public ResponseEntity GetAppointmentDetails(@PathVariable int appointmentId){
        Optional<AppointmentDao> existingAppointment = this.appointmentService.getAppointmentById(appointmentId);
        if (existingAppointment.isEmpty()){
            throw new RecordNotFoundException("Requested resource is not available");
        }

        AppointmentDao appointmentDao = existingAppointment.get();
        return new ResponseEntity(modelMapper.map(appointmentDao, AppointmentDto.class), HttpStatus.OK);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity GetAppointmentsByUser(@PathVariable String userId){

        List<AppointmentDao> appointmentDaoList = this.appointmentService.getAppointmentByUserId(userId);
        List<AppointmentDto> appointmentDtoList = new ArrayList<>();
        for(AppointmentDao appointmentDao : appointmentDaoList){
            appointmentDtoList.add(modelMapper.map(appointmentDao, AppointmentDto.class));
        }

        return new ResponseEntity(appointmentDtoList, HttpStatus.OK);
    }

    @PostMapping("/prescriptions")
    public ResponseEntity savePrescription(@RequestBody PrescriptionDto prescriptionDto){
        ResponseEntity<AppointmentDto> appointmentDto = this.GetAppointmentDetails(prescriptionDto.getAppointmentId());

        DoctorDto doctorDto = this.userClient.getDoctorDetails(prescriptionDto.getDoctorId());
        UserDto userDto = this.userClient.getUserDetails(prescriptionDto.getUserId());
        if(Objects.requireNonNull(appointmentDto.getBody()).getStatus().equals(PaymentStatus.PENDING_PAYMENT.name()))
        {
            var errorResponse = new ErrorResponse();
            errorResponse.setErrorCode(ErrorCode.ERR_PAYMENT_PENDING.name());
            errorResponse.setErrorMessage("Prescription cannot be issued since the payment status is pending");
            return new ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST);
        }

        prescriptionDto.setDoctorName(doctorDto.getFirstName());
        prescriptionDto.setUserName(userDto.getFirstName());
        prescriptionDto.setUserEmailId(userDto.getEmailId());
        PrescriptionDao prescriptionDao = modelMapper.map(prescriptionDto, PrescriptionDao.class);
        prescriptionService.savePrescription(prescriptionDao);
        this.kafkaProducerService.sendMessage(prescriptionDto);
        return new ResponseEntity(HttpStatus.OK);
    }
}
