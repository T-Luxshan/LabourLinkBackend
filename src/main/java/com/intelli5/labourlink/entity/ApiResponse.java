package com.intelli5.labourlink.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ApiResponse {
    private String message;
    private Boolean status;
}
