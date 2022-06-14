package com.upgrad.userservice.dto;
import com.upgrad.userservice.validator.DateFormatValidator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private String id;
    @Size(max = 20)
    @NotBlank
    private String firstName, lastName;
    @DateFormatValidator
    private String dob;
    @Email
    @NotBlank
    private String emailId;
    @Pattern(regexp = "(^$|[0-9]{10})")
    private String mobile;
    private String createdDate;
}
