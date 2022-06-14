package com.upgrad.userservice.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document("users")
public class UserDao {
    @Id
    private String id;
    private String firstName, lastName;
    private String dob;
    private String emailId;
    private String mobile;
    private String createdDate;
}
