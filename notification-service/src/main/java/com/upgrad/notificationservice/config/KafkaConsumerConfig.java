package com.upgrad.notificationservice.config;

import com.upgrad.notificationservice.dto.AppointmentDto;
import com.upgrad.notificationservice.dto.DoctorDto;
import com.upgrad.notificationservice.dto.PrescriptionDto;
import com.upgrad.notificationservice.dto.UserDto;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

    @Value("${bootstrap.server}")
    private String bootstrapServers;

    @Value("${doctor.consumer.group.id}")
    private String doctorGroupId;

    @Value("${user.consumer.group.id}")
    private String userGroupId;

    @Value("${prescription.consumer.group.id}")
    private String prescriptionGroupId;

    @Value("${appointment.consumer.group.id}")
    private String appointmentGroupId;

    @Bean
    ConsumerFactory<String, DoctorDto> doctorConsumerFactory(){
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ConsumerConfig.GROUP_ID_CONFIG, doctorGroupId);
        return new DefaultKafkaConsumerFactory<>(getConfig(configProps), new StringDeserializer(), new JsonDeserializer<>(DoctorDto.class));
    }

    @Bean
    ConsumerFactory<String, UserDto> userConsumerFactory(){
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ConsumerConfig.GROUP_ID_CONFIG, userGroupId);
        return new DefaultKafkaConsumerFactory<>(getConfig(configProps), new StringDeserializer(), new JsonDeserializer<>(UserDto.class));
    }

    @Bean
    ConsumerFactory<String, PrescriptionDto> prescriptionConsumerFactory(){
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ConsumerConfig.GROUP_ID_CONFIG, prescriptionGroupId);
        return new DefaultKafkaConsumerFactory<>(getConfig(configProps), new StringDeserializer(), new JsonDeserializer<>(PrescriptionDto.class));
    }

    @Bean
    ConsumerFactory<String, AppointmentDto> appointmentConsumerFactory(){
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ConsumerConfig.GROUP_ID_CONFIG, appointmentGroupId);
        return new DefaultKafkaConsumerFactory<>(getConfig(configProps), new StringDeserializer(), new JsonDeserializer<>(AppointmentDto.class));
    }

    @Bean
    ConcurrentKafkaListenerContainerFactory doctorConcurrentKafkaListenerContainerFactory(){
        ConcurrentKafkaListenerContainerFactory concurrentKafkaListenerContainerFactory = new ConcurrentKafkaListenerContainerFactory();
        concurrentKafkaListenerContainerFactory.setConsumerFactory(doctorConsumerFactory());
        return concurrentKafkaListenerContainerFactory;
    }

    @Bean
    ConcurrentKafkaListenerContainerFactory userConcurrentKafkaListenerContainerFactory(){
        ConcurrentKafkaListenerContainerFactory concurrentKafkaListenerContainerFactory = new ConcurrentKafkaListenerContainerFactory();
        concurrentKafkaListenerContainerFactory.setConsumerFactory(userConsumerFactory());
        return concurrentKafkaListenerContainerFactory;
    }

    @Bean
    ConcurrentKafkaListenerContainerFactory prescriptionConcurrentKafkaListenerContainerFactory(){
        ConcurrentKafkaListenerContainerFactory concurrentKafkaListenerContainerFactory = new ConcurrentKafkaListenerContainerFactory();
        concurrentKafkaListenerContainerFactory.setConsumerFactory(prescriptionConsumerFactory());
        return concurrentKafkaListenerContainerFactory;
    }

    @Bean
    ConcurrentKafkaListenerContainerFactory appointmentConcurrentKafkaListenerContainerFactory(){
        ConcurrentKafkaListenerContainerFactory concurrentKafkaListenerContainerFactory = new ConcurrentKafkaListenerContainerFactory();
        concurrentKafkaListenerContainerFactory.setConsumerFactory(appointmentConsumerFactory());
        return concurrentKafkaListenerContainerFactory;
    }

    private Map<String, Object> getConfig( Map<String, Object> configProps) {
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        configProps.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        configProps.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);
        return configProps;
    }
}
