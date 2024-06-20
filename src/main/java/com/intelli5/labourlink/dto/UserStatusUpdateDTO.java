package com.intelli5.labourlink.dto;

import com.intelli5.labourlink.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserStatusUpdateDTO {
    private Status status;
}
