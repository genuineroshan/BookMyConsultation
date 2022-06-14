package com.upgrad.doctorservice.config;

import com.upgrad.doctorservice.dto.RatingsDto;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

    @Value("${bootstrap.server}")
    private String bootstrapServers;

    @Value("${ratings.consumer.group.id}")
    private String doctorGroupId;

    @Bean
    ConsumerFactory<String, RatingsDto> ratingConsumerFactory(){
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ConsumerConfig.GROUP_ID_CONFIG, doctorGroupId);
        return new DefaultKafkaConsumerFactory<>(getConfig(configProps), new StringDeserializer(), new JsonDeserializer<>(RatingsDto.class));
    }

    @Bean
    ConcurrentKafkaListenerContainerFactory ratingsConcurrentKafkaListenerContainerFactory(){
        ConcurrentKafkaListenerContainerFactory concurrentKafkaListenerContainerFactory = new ConcurrentKafkaListenerContainerFactory();
        concurrentKafkaListenerContainerFactory.setConsumerFactory(ratingConsumerFactory());
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
