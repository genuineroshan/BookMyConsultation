package com.upgrad.doctorservice.controller;

import com.upgrad.doctorservice.dao.DoctorDao;
import com.upgrad.doctorservice.dto.DoctorDto;
import com.upgrad.doctorservice.enumeration.RegistrationStatus;
import com.upgrad.doctorservice.exception.RecordNotFoundException;
import com.upgrad.doctorservice.service.DoctorService;
import com.upgrad.doctorservice.service.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

// TODO: ROLE implementation (USER and ADMIN)
@RequiredArgsConstructor
@RestController
@RequestMapping("/doctors")
public class DoctorServiceController {

    private static final String GENERAL_PHYSICIAN = "GENERAL_PHYSICIAN";

    private final DoctorService doctorService;
    private final ModelMapper modelMapper;
    private final KafkaProducerService kafkaProducerService;

    @PostMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity RegisterDoctor(@RequestBody @Valid DoctorDto doctorDto) {
        doctorDto.setRegistrationDate(this.GetCurrentLocalDate());
        doctorDto.setStatus(RegistrationStatus.PENDING.name());
        if(doctorDto.getSpeciality() == null || doctorDto.getSpeciality().isBlank()){
            doctorDto.setSpeciality(GENERAL_PHYSICIAN);
        }

        DoctorDao doctorDao = modelMapper.map(doctorDto, DoctorDao.class);
        this.doctorService.saveDoctor(doctorDao);
        this.kafkaProducerService.sendMessage(doctorDto);
        return new ResponseEntity<>(modelMapper.map(doctorDao, DoctorDto.class), HttpStatus.CREATED);
    }

    @CrossOrigin
    @PostMapping(value = "/{doctorId}/documents")
    public  ResponseEntity UploadDocuments(@PathVariable String doctorId, @RequestParam("files") MultipartFile[] files) throws IOException {
        Optional<DoctorDao> doctorDao = this.doctorService.getDoctor(doctorId);
        if(doctorDao.isEmpty()){
            throw new RecordNotFoundException("Requested resource is not available");
        }

        for(MultipartFile file: files) {
            this.doctorService.saveDocuments(doctorId, file);
        }

        return new ResponseEntity<>("Files Uploaded Successfully", HttpStatus.OK);
    }

    @PutMapping(value = "/{doctorId}/approve")
    public  ResponseEntity ApproveRegistration(@PathVariable String doctorId, @RequestBody DoctorDto doctorDto) {
        Optional<DoctorDao> doctorDao = this.doctorService.getDoctor(doctorId);
        if(doctorDao.isEmpty()){
            throw new RecordNotFoundException("Requested resource is not available");
        }

        DoctorDao doctorDetails = doctorDao.get();
        doctorDetails.setStatus(RegistrationStatus.ACTIVE.name());
        return updateVerificationStatus(doctorDto, doctorDetails);
    }

    @PutMapping(value = "/{doctorId}/reject")
    public  ResponseEntity RejectRegistration(@PathVariable String doctorId, @RequestBody DoctorDto doctorDto) {
        Optional<DoctorDao> doctorDao = this.doctorService.getDoctor(doctorId);
        if(doctorDao.isEmpty()){
            throw new RecordNotFoundException("Requested resource is not available");
        }

        DoctorDao doctorDetails = doctorDao.get();
        doctorDetails.setStatus(RegistrationStatus.REJECTED.name());
        return updateVerificationStatus(doctorDto, doctorDetails);
    }

    @GetMapping(value = "")
    public ResponseEntity GetDoctors(@RequestParam String status, @RequestParam String speciality){
        List<DoctorDao> doctorDaoList = this.doctorService.getDoctorByStatusAndSpeciality(status, speciality);
        return new ResponseEntity<>(doctorDaoList, HttpStatus.OK);
    }

    @GetMapping(value = "/{doctorId}")
    public ResponseEntity GetById(@PathVariable String doctorId){
        Optional<DoctorDao> doctorDao = this.doctorService.getDoctor(doctorId);
        if(doctorDao.isEmpty()){
            throw new RecordNotFoundException("Requested resource is not available");
        }

        return new ResponseEntity(modelMapper.map(doctorDao.get(), DoctorDto.class), HttpStatus.OK);
    }

    @GetMapping(value = "/{doctorId}/documents/metadata")
    public ResponseEntity GetDocuments(@PathVariable String doctorId){
         /*
        TODO:
         load doctor details by ID from nosql DB.
         Throw RecordNotFoundException if doctor not found.
         Get document list uploaded by doctor from s3
         */

        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(value = "/doctors/{doctorId}/documents/{documentName}")
    public ResponseEntity GetDocuments(@PathVariable String doctorId, @PathVariable String documentName){
         /*
        TODO:
         load doctor details by ID from nosql DB.
         Throw RecordNotFoundException if doctor not found.
         Verify if document is accessible by the requested doctor.
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

    private ResponseEntity updateVerificationStatus(DoctorDto doctorDto, DoctorDao doctorDetails) {
        doctorDetails.setVerificationDate(this.GetCurrentLocalDate());
        doctorDetails.setVerifiedBy(doctorDto.getVerifiedBy());
        doctorDetails.setVerifierComments(doctorDto.getVerifierComments());
        this.doctorService.saveDoctor(doctorDetails);
        DoctorDto doctor = modelMapper.map(doctorDetails, DoctorDto.class);
        this.kafkaProducerService.sendMessage(doctor);
        return new ResponseEntity(modelMapper.map(doctorDetails, DoctorDto.class), HttpStatus.OK);
    }
}
