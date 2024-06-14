package com.intelli5.labourlink.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder



public class LabourProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @OneToOne
    @JoinColumn(name = "labour_email", referencedColumnName = "email")
    @JsonIgnore
    Labour labour;


    @Column(name = "about_me")
    private String aboutMe;

private String gender;

@ElementCollection
@Column(name = "languages")
@Enumerated(EnumType.STRING)
    private List<String> languages;



}
