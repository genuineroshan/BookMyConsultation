package com.upgrad.doctorservice.service;

import com.upgrad.doctorservice.dao.DoctorDao;
import com.upgrad.doctorservice.dto.RatingsDto;
import com.upgrad.doctorservice.exception.RecordNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class KafkaConsumerService {
    private final DoctorService doctorService;

    @KafkaListener(topics="${doctor.ratings.topic}", groupId="${ratings.consumer.group.id}",containerFactory = "ratingsConcurrentKafkaListenerContainerFactory")
    public void listenToRatings(@Payload List<RatingsDto> ratingsDtoList) {
        for(RatingsDto rating: ratingsDtoList){
            Optional<DoctorDao> doctor = this.doctorService.getDoctor(rating.getDoctorId());
            if(doctor.isEmpty()){
                throw new RecordNotFoundException("Requested resource is not available");
            }

            DoctorDao doctorDao = doctor.get();

            double calculatedRating = (doctorDao.getNoOfRatings() * doctorDao.getRating() + rating.getRating()) /
                    (doctorDao.getNoOfRatings() + 1);
            doctorDao.setNoOfRatings(doctorDao.getNoOfRatings() + 1);
            doctorDao.setRating(calculatedRating);
            this.doctorService.saveDoctor(doctorDao);
        }
    }
}
