package com.upgrad.ratingservice.controller;
import com.upgrad.ratingservice.dto.RatingsDto;
import com.upgrad.ratingservice.service.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ratings")
public class RatingServiceController {
    private final KafkaProducerService kafkaProducerService;
    @PostMapping("")
        public ResponseEntity doPayment(@RequestBody RatingsDto ratingsDto){
        kafkaProducerService.sendMessage(ratingsDto);
        return new ResponseEntity(HttpStatus.OK);
    }
}
