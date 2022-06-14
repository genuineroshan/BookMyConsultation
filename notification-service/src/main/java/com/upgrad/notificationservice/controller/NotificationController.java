package com.upgrad.notificationservice.controller;
import com.upgrad.notificationservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/notifications")
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping("/verify")
    public ResponseEntity verifyEmail(@RequestParam("email") String emailId){
        this.notificationService.verifyEmail(emailId);
        return ResponseEntity.ok().build();
    }
}
