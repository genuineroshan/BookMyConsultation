package com.upgrad.appointmentservice.service;
import com.upgrad.appointmentservice.dto.AppointmentDto;
import com.upgrad.appointmentservice.dto.PrescriptionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {
    private final KafkaTemplate<String, PrescriptionDto> kafkaTemplate;

    private final KafkaTemplate<String, AppointmentDto> appointmentKafkaTemplate;

    @Value("${kafka.topic}")
    private String topic;

    @Value("${appointment.topic}")
    private String appointmentTopic;

    public void sendMessage(PrescriptionDto prescriptionDto){
        kafkaTemplate.send(topic, prescriptionDto);
    }

    public void sendAppointmentMessage(AppointmentDto appointmentDto){
        appointmentKafkaTemplate.send(appointmentTopic, appointmentDto);
    }
}
