package com.intelli5.labourlink.dto;

import com.intelli5.labourlink.entity.Status;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConnectedUsersDTO {
    private String name;
    private String email;
    private String mobileNumber;
    private Status status;
}
