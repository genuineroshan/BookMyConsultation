package com.upgrad.doctorservice.service;

import com.upgrad.doctorservice.dto.DoctorDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {
    private final KafkaTemplate<String, DoctorDto> kafkaTemplate;

    @Value("${kafka.topic}")
    private String topic;

    public void sendMessage(DoctorDto doctorDto){
        kafkaTemplate.send(topic, doctorDto);
    }
}
