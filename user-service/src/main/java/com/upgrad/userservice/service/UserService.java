package com.upgrad.userservice.service;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.upgrad.userservice.dao.UserDao;
import com.upgrad.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ObjectMetadata objectMetadata;

    @Value("${access.key}")
    private String accessKey;

    @Value("${secret.key}")
    private String secretKey;

    @Value("${s3.bucket.name}")
    private String BucketName;

    public void saveUser(UserDao doctorDao){
        userRepository.save(doctorDao);
    }

    public Optional<UserDao> getUserById(String doctorId){
        return userRepository.findById(doctorId);
    }

    public void saveDocuments(String userId, MultipartFile file) throws IOException {
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        AmazonS3 amazonS3 = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.US_EAST_1)
                .build();
        String key = userId + "/"+ file.getOriginalFilename();
        if(!amazonS3.doesBucketExistV2(BucketName)){
            amazonS3.createBucket(BucketName);
        }
        amazonS3.putObject(BucketName, key, file.getInputStream(), objectMetadata);
    }
}
