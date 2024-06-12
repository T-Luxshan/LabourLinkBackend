package com.intelli5.labourlink.dto;


import com.intelli5.labourlink.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String name;
    private String email;
    private String mobileNumber;
    private Status status;
    private String role;

    public UserDTO(String name, String email, String mobileNumber, Status status) {
    }

}