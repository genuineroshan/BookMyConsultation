package com.upgrad.appointmentservice.feign;

import com.upgrad.appointmentservice.dto.DoctorDto;
import com.upgrad.appointmentservice.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Primary
@FeignClient(name = "user-client", url = "http://gateway:9191")
public interface UserClient {

    @GetMapping("/doctors/{doctorId}")
    DoctorDto getDoctorDetails(@PathVariable String doctorId);

    @GetMapping("/users/{userId}")
    UserDto getUserDetails(@PathVariable String userId);
}
