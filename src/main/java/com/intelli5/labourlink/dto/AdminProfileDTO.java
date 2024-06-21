package com.intelli5.labourlink.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor

public class AdminProfileDTO {
    private String name;
    private String email;
}
