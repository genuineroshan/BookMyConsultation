package com.upgrad.notificationservice.service;

import com.upgrad.notificationservice.dto.AppointmentDto;
import com.upgrad.notificationservice.dto.DoctorDto;
import com.upgrad.notificationservice.dto.PrescriptionDto;
import com.upgrad.notificationservice.dto.UserDto;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class KafkaConsumerService {
    private final NotificationService notificationService;

    @KafkaListener(topics="${doctor.registration.topic}", groupId="${doctor.consumer.group.id}",containerFactory = "doctorConcurrentKafkaListenerContainerFactory")
    public void listenToDoctor(@Payload List<DoctorDto> doctorDtoList) throws TemplateException, MessagingException, IOException {
        for(DoctorDto doctor: doctorDtoList){
            this.notificationService.sendEmailToDoctor(doctor);
        }
    }

    @KafkaListener(topics="${user.registration.topic}", groupId="${user.consumer.group.id}",containerFactory = "userConcurrentKafkaListenerContainerFactory")
    public void listenToUser(@Payload List<UserDto> userDtoList) throws TemplateException, MessagingException, IOException {
        for(UserDto user: userDtoList){
            this.notificationService.sendEmailToUser(user);
        }
    }

    @KafkaListener(topics="${prescription.topic}", groupId="${prescription.consumer.group.id}",containerFactory = "prescriptionConcurrentKafkaListenerContainerFactory")
    public void listenToPrescription(@Payload List<PrescriptionDto> prescriptionDtoList) throws TemplateException, MessagingException, IOException {
        for(PrescriptionDto prescription: prescriptionDtoList){
            this.notificationService.sendPrescription(prescription);
        }
    }

    @KafkaListener(topics="${appointment.topic}", groupId="${appointment.consumer.group.id}",containerFactory = "appointmentConcurrentKafkaListenerContainerFactory")
    public void listenToAppointment(@Payload List<AppointmentDto> appointmentDtoList) throws TemplateException, MessagingException, IOException {
        for(AppointmentDto appointment: appointmentDtoList){
            this.notificationService.sendAppointment(appointment);
        }
    }
}
