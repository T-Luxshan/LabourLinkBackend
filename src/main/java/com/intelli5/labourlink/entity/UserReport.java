package com.intelli5.labourlink.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    private String title;

    private String description;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "ReportedBy")
    private User ReportedBy;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "ReportedTo")
    private User ReportedTo;


}
