package com.intelli5.labourlink.dto;



import com.google.auto.value.AutoValue;
import com.intelli5.labourlink.entity.LabourProfile;
import lombok.Builder;
import lombok.Data;



import java.util.List;
@Data
@Builder
public class LabourProfileDTO {
    private String aboutMe;
    private String gender;
    private List<String> languages;
    private String experience;
//    private String location;
}
