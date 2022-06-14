package com.upgrad.appointmentservice.dao;

import com.upgrad.appointmentservice.dto.Medicine;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "prescriptions")
public class PrescriptionDao {
    @Id
    private String id;
    private String appointmentId;
    private String doctorId;
    private String userId;
    private String diagnosis;
    private List<Medicine> medicineList;
    private String doctorName;
    private String userName;
    private String userEmailId;
}
