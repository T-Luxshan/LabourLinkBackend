package com.intelli5.labourlink.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.firebase.database.annotations.NotNull;
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
//@PrimaryKeyJoinColumn(name = "email")


public class LabourProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @OneToOne
    @JsonIgnore
    Labour labour;


    @Column(name = "about_me")
    private String aboutMe;

private String gender;

@ElementCollection
//@CollectionTable(name = "labour_profile_languages", joinColumns = @JoinColumn(name = "labour_profile_email"))
@Column(name = "languages")
@Enumerated(EnumType.STRING)
    private List<String> languages;

private String location;



}
