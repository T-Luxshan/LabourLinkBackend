package com.intelli5.labourlink.dto;



import com.google.auto.value.AutoValue;
import com.intelli5.labourlink.entity.LabourProfile;
import lombok.Builder;
import lombok.Data;



import java.util.List;
@Data

public class LabourProfileDTO {
private String email;
private String aboutMe;
private String gender;
private List<String> languages;
private String location;

    public LabourProfileDTO(Builder builder) {
        this.aboutMe = builder.aboutMe;
        this.gender = builder.gender;
        this.languages = builder.languages;
        this.location = builder.location;
    }




    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String aboutMe;
        private String gender;
        private List<String> languages;
        private String location;

        public Builder aboutMe(String aboutMe) {
            this.aboutMe = aboutMe;
            return this;
        }

        public Builder gender(String gender) {
            this.gender = gender;
            return this;
        }

        public Builder languages(List<String> languages) {
            this.languages = languages;
            return this;
        }

        public Builder location(String location) {
            this.location = location;
            return this;
        }

        public LabourProfileDTO build() {
            return new LabourProfileDTO(this);
        }
    }
//
//public LabourProfileDTO() {
//
//    }
//
//    public static LabourProfileDTOBuilder builder() {
//        return new LabourProfileDTOBuilder();
//    }
//
//    public static class LabourProfileDTOBuilder {
//        // Builder methods
//    }
//

}
