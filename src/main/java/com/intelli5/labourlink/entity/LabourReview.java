package com.intelli5.labourlink.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LabourReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @NonNull
    private String jobRole;
    @NonNull
//    private String workTitle;
    private String description;
    @NonNull
    private Double rating;

    @ManyToOne
    @JoinColumn(name = "customer_email")
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "labour_email")
    private Labour labour;
    private LocalDateTime reviewPostAt;

    public void setLabour(Labour labour) {
        this.labour = labour;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
