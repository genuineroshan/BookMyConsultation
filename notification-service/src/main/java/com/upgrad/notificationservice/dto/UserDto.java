package com.upgrad.notificationservice.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private String id;
    private String firstName, lastName;
    private String dob;
    private String emailId;
    private String mobile;
    private String createdDate;
}
