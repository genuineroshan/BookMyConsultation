package com.upgrad.doctorservice;

import com.amazonaws.services.s3.model.ObjectMetadata;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableAspectJAutoProxy
@EnableMongoRepositories
public class DoctorServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DoctorServiceApplication.class, args);
	}

	@Bean
	ModelMapper modelMapper(){
		return  new ModelMapper();
	}

	@Bean
	ObjectMetadata objectMetadata(){
		return new ObjectMetadata();
	}

}
