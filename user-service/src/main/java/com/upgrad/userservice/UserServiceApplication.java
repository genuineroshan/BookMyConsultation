package com.upgrad.userservice;

import com.amazonaws.services.s3.model.ObjectMetadata;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class UserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
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
