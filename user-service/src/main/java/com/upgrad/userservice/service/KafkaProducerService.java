package com.upgrad.userservice.service;

import com.upgrad.userservice.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {
    private final KafkaTemplate<String, UserDto> kafkaTemplate;

    @Value("${kafka.topic}")
    private String topic;

    public void sendMessage(UserDto userDto){
        kafkaTemplate.send(topic, userDto);
    }
}
