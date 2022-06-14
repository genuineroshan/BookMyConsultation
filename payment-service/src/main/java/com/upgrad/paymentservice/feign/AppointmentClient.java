package com.upgrad.paymentservice.feign;

import com.upgrad.paymentservice.dto.AppointmentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Primary
@FeignClient(name = "appointment-client", url = "http://gateway:9191")
public interface AppointmentClient {

    @GetMapping("${appointmentDetails.Path}")
    AppointmentDto getAppointmentDetails(@PathVariable int appointmentId);

    @PostMapping("/appointments/")
    void updateAppointmentStatus(@RequestBody AppointmentDto appointmentDto);
}
