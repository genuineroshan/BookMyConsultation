package com.upgrad.ratingservice.service;
import com.upgrad.ratingservice.dto.RatingsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {
    private final KafkaTemplate<String, RatingsDto> kafkaTemplate;

    @Value("${kafka.topic}")
    private String topic;

    public void sendMessage(RatingsDto ratingsDto) {
        kafkaTemplate.send(topic, ratingsDto);
    }
}
