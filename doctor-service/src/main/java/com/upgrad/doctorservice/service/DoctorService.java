package com.upgrad.doctorservice.service;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.upgrad.doctorservice.dao.DoctorDao;
import com.upgrad.doctorservice.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class DoctorService {
    private final DoctorRepository doctorRepository;
    private final ObjectMetadata objectMetadata;

    @Value("${access.key}")
    private String accessKey;

    @Value("${secret.key}")
    private String secretKey;

    @Value("${s3.bucket.name}")
    private String BucketName;

    public void saveDoctor(DoctorDao doctorDao){
        doctorRepository.save(doctorDao);
    }

    public Optional<DoctorDao> getDoctor(String doctorId){
        return doctorRepository.findById(doctorId);
    }

    public List<DoctorDao> getDoctorByStatusAndSpeciality(String status, String speciality){
        return doctorRepository.findAllByStatusAndSpeciality(status, speciality);
    }

    public void saveDocuments(String doctorId, MultipartFile file) throws IOException {
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        AmazonS3 amazonS3 = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.US_EAST_1)
                .build();
        String key = doctorId + "/"+ file.getOriginalFilename();
        if(!amazonS3.doesBucketExistV2(BucketName)){
            amazonS3.createBucket(BucketName);
        }
        amazonS3.putObject(BucketName, key, file.getInputStream(), objectMetadata);
    }
}
