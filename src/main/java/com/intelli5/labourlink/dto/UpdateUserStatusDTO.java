package com.intelli5.labourlink.dto;

import com.intelli5.labourlink.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateUserStatusDTO {
    private Status status;
}
