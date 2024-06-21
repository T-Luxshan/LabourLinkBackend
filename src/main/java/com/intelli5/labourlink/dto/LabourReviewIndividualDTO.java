package com.intelli5.labourlink.dto;

import com.intelli5.labourlink.entity.Customer;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Data
public class LabourReviewIndividualDTO {
    private Customer customer;
    private String jobRole;
    private String description;
    private Double rating;

}
