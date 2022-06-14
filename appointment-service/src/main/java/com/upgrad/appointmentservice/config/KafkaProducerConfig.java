package com.upgrad.appointmentservice.config;
import com.upgrad.appointmentservice.dto.AppointmentDto;
import com.upgrad.appointmentservice.dto.PrescriptionDto;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {
    @Value("${bootstrap.server}")
    private String bootstrapServers;

    @Bean
    ProducerFactory<String, PrescriptionDto> producerFactory(){
        return new DefaultKafkaProducerFactory<>(getConfig());
    }

    @Bean
    KafkaTemplate<String, PrescriptionDto> kafkaTemplate(){
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    ProducerFactory<String, AppointmentDto> appointmentProducerFactory(){
        return new DefaultKafkaProducerFactory<>(getConfig());
    }

    @Bean
    KafkaTemplate<String, AppointmentDto> appointmentKafkaTemplate(){
        return new KafkaTemplate<>(appointmentProducerFactory());
    }

    private Map<String, Object> getConfig() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return configProps;
    }
}
