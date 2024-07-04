package com.intelli5.labourlink.dto;

import com.intelli5.labourlink.entity.Customer;
import com.intelli5.labourlink.entity.Labour;
import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LabourReviewAdminDTO {
    Integer Id;
    String jobRole;
    //    String workTitle;
    String description;
    Double rating;
    String labourName;
    String customerName;
    String customerEmail;
    private LocalDateTime reviewPostAt;
    private String customerImage;


}
