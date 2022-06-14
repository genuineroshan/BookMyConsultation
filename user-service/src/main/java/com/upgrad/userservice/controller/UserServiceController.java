package com.upgrad.userservice.controller;

import com.upgrad.userservice.service.KafkaProducerService;
import com.upgrad.userservice.service.UserService;
import com.upgrad.userservice.dao.UserDao;
import com.upgrad.userservice.dto.UserDto;
import com.upgrad.userservice.exception.RecordNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UserServiceController {

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final KafkaProducerService kafkaProducerService;

    @PostMapping("")
    public ResponseEntity RegisterUser(@RequestBody @Valid UserDto userDto) {
        userDto.setCreatedDate(this.GetCurrentLocalDate());
        UserDao userDao = modelMapper.map(userDto, UserDao.class);
        this.userService.saveUser(userDao);
        this.kafkaProducerService.sendMessage(userDto);
        return new ResponseEntity<>(modelMapper.map(userDao, UserDto.class), HttpStatus.CREATED);
    }

    @CrossOrigin
    @PostMapping(value = "/{userId}/documents")
    public  ResponseEntity UploadDocuments(@PathVariable String userId, @RequestParam("files") MultipartFile[] files) throws IOException {
        Optional<UserDao> userDao = this.userService.getUserById(userId);
        if(userDao.isEmpty()){
            throw new RecordNotFoundException("Requested resource is not available");
        }

        for(MultipartFile file: files) {
            this.userService.saveDocuments(userId, file);
        }
        return new ResponseEntity<>("Files Uploaded Successfully", HttpStatus.OK);
    }

    @GetMapping(value = "/{userId}")
    public ResponseEntity GetById(@PathVariable String userId){
        Optional<UserDao> userDao = this.userService.getUserById(userId);
        if(userDao.isEmpty()){
            throw new RecordNotFoundException("Requested resource is not available");
        }

        return new ResponseEntity(modelMapper.map(userDao.get(), UserDto.class), HttpStatus.OK);
    }

    @GetMapping(value = "/{userId}/documents/metadata")
    public ResponseEntity GetDocuments(@PathVariable String userId){
         /*
        TODO:
         load user details by ID from nosql DB.
         Throw RecordNotFoundException if user not found.
         Get document list uploaded by user from s3
         */

        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(value = "/users/{userId}/documents/{documentName}")
    public ResponseEntity DownloadDocument(@PathVariable String userId, @PathVariable String documentName){
         /*
        TODO:
         load user details by ID from nosql DB.
         Throw RecordNotFoundException if user not found.
         Verify if document is accessible by the requested user.
         load document as per document name from AWS S3.
         */

        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * Returns the current local date formatted as "yyyy-MM-dd".
     */
    private String GetCurrentLocalDate(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime currentDate = LocalDateTime.now();
        return formatter.format(currentDate);
    }
}
